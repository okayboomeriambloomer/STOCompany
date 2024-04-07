package ru.bdproject.STO.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IContext;
import org.thymeleaf.extras.springsecurity6.util.SpringSecurityContextUtils;
import ru.bdproject.STO.models.Car;
import ru.bdproject.STO.models.Person;
import ru.bdproject.STO.models.RepairStation;
import ru.bdproject.STO.models.TypeOfRepair;
import ru.bdproject.STO.services.MainService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class MainController {

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/forbidden")
    public String forbidden(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        System.out.println(new ArrayList<GrantedAuthority>(userDetails.getAuthorities()).get(0).getAuthority());
        model.addAttribute("role", new ArrayList<GrantedAuthority>(userDetails.getAuthorities()).get(0).getAuthority());
        return "/forbidden";
    }

    @GetMapping("/")
    public String homePage() {
//        mainService.test();
        return "home-page";
    }

    @GetMapping("/service-request-process")
    public String getRequest(@ModelAttribute("person") Person person,
                             @ModelAttribute("car") Car car,
                             Model model) {
        model.addAttribute("repairStations", mainService.getAllRepairStations());
        model.addAttribute("typesOfRepair", mainService.getAllTypeOfRepair());
        model.addAttribute("date", LocalDateTime.now().plusDays(1).withHour(12).withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return "get-request";
    }

    @PostMapping("/service-accept-process")
    public String acceptRequest(@ModelAttribute("person") @Valid Person person, BindingResult bindingResultPerson,
                                @ModelAttribute("car") @Valid Car car, BindingResult bindingResultCar,
                                @RequestParam("repairStationId") Integer repairStationId,
                                @RequestParam("typeOfRepairId") Integer typeOfRepairId,
                                @RequestParam("date") LocalDateTime localDateTime,
                                Model model) {
        if (car.getYear().before(Date.valueOf(LocalDate.of(1960, 1, 1)))) {
            bindingResultCar.rejectValue("year", "error.year", "Год выпуска слишком мал");
        }
        if (bindingResultCar.hasErrors() || bindingResultPerson.hasErrors()) {
            model.addAttribute("repairStations", mainService.getAllRepairStations());
            model.addAttribute("typesOfRepair", mainService.getAllTypeOfRepair());
            model.addAttribute("date", LocalDateTime.now()
                    .plusDays(1).withHour(12).withMinute(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            return "get-request";
        }

        System.out.println(person);
        System.out.println(car);
        System.out.println(repairStationId);
        System.out.println(typeOfRepairId);
        System.out.println(localDateTime);

        mainService.addServiceToUpcoming(person, car, repairStationId, typeOfRepairId, localDateTime);

        model.addAttribute("date", localDateTime);
        model.addAttribute("repairStation", mainService.getRepairStation(repairStationId));
        return "get-success-request";
    }
}
