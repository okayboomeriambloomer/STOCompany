package ru.bdproject.STO.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bdproject.STO.models.RepairStation;
import ru.bdproject.STO.models.TypeOfRepair;
import ru.bdproject.STO.services.MainService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    MainService mainService;

    @Autowired
    public AdminController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping
    public String getAdminPage() {
        return "/admin-page";
    }

    @GetMapping("/upcoming-services")
    public String getUpcomingServices(Model model) {
        model.addAttribute("upcomingServices", mainService.getAllUpcomingServices());
        return "/upcoming-services";
    }

    @PatchMapping("/{id}/to-history")
    public String replaceToHistory(@PathVariable("id") Integer id) {
        mainService.replaceServiceToHistory(id);
        return "redirect:/admin/upcoming-services";
    }

    @GetMapping("/repair-stations")
    public String getRepairStations(Model model) {
        model.addAttribute("repairStations", mainService.getAllRepairStations());
        return "/repair-station/get-all";
    }

    @GetMapping("/repair-station/{id}")
    public String updateRepairStation(@PathVariable("id") Integer repairStationId,
                                      Model model) {
        model.addAttribute("station", mainService.getRepairStation(repairStationId));
        return "/repair-station/update";
    }

    @PatchMapping("/repair-station/{id}")
    public String updateRepairStation(@ModelAttribute("station") @Valid RepairStation repairStation,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/repair-station/update";
        }
        if (mainService.containsRepairStation(repairStation)) {
            bindingResult.rejectValue("address", "error.address", "Точка с таким адресом уже существует");
            return "/repair-station/update";
        }
        mainService.updateRepairStation(repairStation);
        return "/repair-station/update";
    }

    @DeleteMapping("/repair-station/{id}")
    public String deleteRepairStation(@PathVariable("id") Integer repairStationId) {
        mainService.deleteRepairStation(repairStationId);
        return "redirect:/admin/repair-stations";
    }

    @GetMapping("/repair-station/new")
    public String createNewRepairStation(@ModelAttribute("station") RepairStation repairStation) {
        return "/repair-station/new";
    }

    @PostMapping("/repair-station/new")
    public String addNewRepairStation(@ModelAttribute("station") @Valid RepairStation repairStation,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/repair-station/new";
        }
        if (mainService.containsRepairStation(repairStation)) {
            bindingResult.rejectValue("address", "error.address", "Точка с таким адресом уже существует");
            return "/repair-station/new";
        }
        mainService.addRepairStation(repairStation);
        return "redirect:/admin/repair-stations";
    }

    @GetMapping("/type-of-repairs")
    public String getTypeOfRepairs(Model model) {
        model.addAttribute("repairs", mainService.getAllTypeOfRepair());
        return "/type-of-repair/get-all";
    }

    @GetMapping("/type-of-repair/{id}")
    public String updateTypeOfRepair(@PathVariable("id") Integer typeOfRepairId,
                                     Model model) {
        model.addAttribute("repair", mainService.getTypeOfRepair(typeOfRepairId));
        return "/type-of-repair/update";
    }

    @PatchMapping("/type-of-repair/{id}")
    public String updateTypeOfRepair(@ModelAttribute("repair") @Valid TypeOfRepair typeOfRepair,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/type-of-repair/update";
        }
        if (mainService.containsTypeOfRepair(typeOfRepair)) {
            bindingResult.rejectValue("name", "error.name", "Услуга с таким наименованием уже существует");
            return "/type-of-repair/update";
        }
        mainService.updateTypeOfRepair(typeOfRepair);
        return "/type-of-repair/update";
    }

    @DeleteMapping("/type-of-repair/{id}")
    public String deleteTypeOfRepair(@PathVariable("id") Integer typeOfRepairId) {
        mainService.deleteTypeOfRepair(typeOfRepairId);
        return "redirect:/admin/type-of-repairs";
    }

    @GetMapping("/type-of-repair/new")
    public String createNewTypeOfRepair(@ModelAttribute("repair") TypeOfRepair typeOfRepair) {
        return "/type-of-repair/new";
    }

    @PostMapping("/type-of-repair/new")
    public String addNewTypeOfRepair(@ModelAttribute("repair") @Valid TypeOfRepair typeOfRepair,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/type-of-repair/new";
        }
        if (mainService.containsTypeOfRepair(typeOfRepair)) {
            bindingResult.rejectValue("name", "error.name", "Услуга с таким наименованием уже существует");
            return "/type-of-repair/new";
        }
        mainService.addTypeOfRepair(typeOfRepair);
        return "redirect:/admin/type-of-repairs";
    }


    @GetMapping("/history-services")
    public String getHistoryServices(Model model) {
        model.addAttribute("historyServices", mainService.getAllHistoryServices());
        return "/history-services";
    }

    @PatchMapping("/{id}/to-upcoming")
    public String replaceToUpcoming(@PathVariable("id") Integer id) {
        mainService.replaceServiceToUpcoming(id);
        return "redirect:/admin/history-services";
    }
}
