package ru.bdproject.STO.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bdproject.STO.DAO.*;
import ru.bdproject.STO.models.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MainService {
    private final CarDAO carDAO;
    private final PersonDAO personDAO;
    private final RepairStationDAO repairStationDAO;
    private final TypeOfRepairDAO typeOfRepairDAO;
    private final HistoryOfServiceDAO historyOfServiceDAO;
    private final UpcomingServiceDAO upcomingServiceDAO;

    @Autowired
    public MainService(CarDAO carDAO,
                       PersonDAO personDAO,
                       RepairStationDAO repairStationDAO,
                       TypeOfRepairDAO typeOfRepairDAO,
                       HistoryOfServiceDAO historyOfServiceDAO,
                       UpcomingServiceDAO upcomingServiceDAO) {
        this.carDAO = carDAO;
        this.personDAO = personDAO;
        this.repairStationDAO = repairStationDAO;
        this.typeOfRepairDAO = typeOfRepairDAO;
        this.historyOfServiceDAO = historyOfServiceDAO;
        this.upcomingServiceDAO = upcomingServiceDAO;
    }

    public List<Car> getAllCar() {
        return carDAO.getAllCar();
    }

    public List<Person> getAllPerson() {
        return personDAO.getAll();
    }

    public List<RepairStation> getAllRepairStations() {
        return repairStationDAO.getAll();
    }

    public List<TypeOfRepair> getAllTypeOfRepair() {
        return typeOfRepairDAO.getAll();
    }

    @Transactional
    public void test() {
//        carDAO.testCar();
        historyOfServiceDAO.testHistoryOfService();
    }

    public RepairStation getRepairStation(Integer repairStationId) {
        return repairStationDAO.get(repairStationId);
    }

    @Transactional
    public void addServiceToUpcoming(Person person, Car car, Integer repairStationId, Integer typeOfRepairId, LocalDateTime localDateTime) {
        person = personDAO.saveIfNotExist(person);
        car = carDAO.saveIfNotExist(car);
        RepairStation repairStation = repairStationDAO.get(repairStationId);
        TypeOfRepair typeOfRepair = typeOfRepairDAO.get(typeOfRepairId);

        UpcomingService upcomingService = upcomingServiceDAO.saveNewService(person, car, repairStation, typeOfRepair, localDateTime);
        personDAO.saveNewUpcomingService(person, upcomingService);
        carDAO.saveNewUpcomingService(car, upcomingService);
        repairStationDAO.saveNewUpcomingService(repairStation, upcomingService);
        typeOfRepairDAO.saveNewUpcomingService(typeOfRepair, upcomingService);
    }

    public List<UpcomingService> getAllUpcomingServices() {
        return upcomingServiceDAO.getAll();
    }

    @Transactional
    public void replaceServiceToHistory(Integer id) {
        upcomingServiceDAO.replaceServiceToHistory(id);
    }

    public List<HistoryOfService> getAllHistoryServices() {
        return historyOfServiceDAO.getAll();
    }

    @Transactional
    public void replaceServiceToUpcoming(Integer id) {
        historyOfServiceDAO.replaceServiceToUpcoming(id);
    }

    @Transactional
    public void updateRepairStation(RepairStation repairStation) {
        repairStationDAO.update(repairStation);
    }

    @Transactional
    public void deleteRepairStation(Integer repairStationId) {
        repairStationDAO.remove(repairStationId);
    }

    @Transactional
    public void addRepairStation(RepairStation repairStation) {
        repairStationDAO.add(repairStation);
    }

    public TypeOfRepair getTypeOfRepair(Integer typeOfRepairId) {
        return typeOfRepairDAO.get(typeOfRepairId);
    }

    @Transactional
    public void addTypeOfRepair(TypeOfRepair typeOfRepair) {
        typeOfRepairDAO.add(typeOfRepair);
    }

    @Transactional
    public void deleteTypeOfRepair(Integer typeOfRepairId) {
        typeOfRepairDAO.remove(typeOfRepairId);
    }

    @Transactional
    public void updateTypeOfRepair(TypeOfRepair typeOfRepair) {
        typeOfRepairDAO.update(typeOfRepair);
    }

    public boolean containsTypeOfRepair(TypeOfRepair typeOfRepair) {
        return typeOfRepairDAO.contains(typeOfRepair);
    }

    public boolean containsRepairStation(RepairStation repairStation) {
        return repairStationDAO.contains(repairStation);
    }
}
