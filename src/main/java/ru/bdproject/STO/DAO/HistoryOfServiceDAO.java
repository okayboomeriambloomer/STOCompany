package ru.bdproject.STO.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bdproject.STO.models.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class HistoryOfServiceDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void testHistoryOfService() {
//        RepairStation repairStation = new RepairStation("Адрес", "МанагерНейм", "81231231212");
//        TypeOfRepair typeOfRepair = new TypeOfRepair("говно", 123.0d);
//        Person person = new Person("Вова", "Лох", "81231231212");
//        Car car = new Car("GAZ", "А111АА790", Date.valueOf(LocalDate.now()));
//        UpcomingService upcomingService = new UpcomingService(
//                repairStation,
//                typeOfRepair,
//                person,
//                car,
//                Date.valueOf(LocalDate.now()));
//
//        System.out.println(typeOfRepair);
//
//        repairStation.setUpcomingServices(Collections.singletonList(upcomingService));
//        typeOfRepair.setUpcomingTypeOfRepair(Collections.singletonList(upcomingService));
//        person.setUpcomingServices(Collections.singletonList(upcomingService));
//        car.setUpcomingServices(Collections.singletonList(upcomingService));
//
//        entityManager.persist(repairStation);
//        entityManager.persist(typeOfRepair);
//        entityManager.persist(person);
//        entityManager.persist(car);
//        entityManager.persist(upcomingService);

        UpcomingService upcomingService = entityManager.createQuery("FROM UpcomingService WHERE id = 4", UpcomingService.class).getSingleResult();
        HistoryOfService newHistory = HistoryOfService.of(upcomingService);
        entityManager.persist(newHistory);
        entityManager.remove(upcomingService);
    }

    public List<HistoryOfService> getAll() {
        return entityManager
                .createQuery("FROM HistoryOfService", HistoryOfService.class)
                .getResultList();
    }

    @Transactional
    public void replaceServiceToUpcoming(Integer id) {
        HistoryOfService historyOfService = entityManager
                .createQuery("FROM HistoryOfService WHERE id = ?1", HistoryOfService.class)
                .setParameter(1, id)
                .getSingleResult();
        UpcomingService newHistory = UpcomingService.of(historyOfService);
        entityManager.persist(newHistory);
        entityManager.remove(historyOfService);
    }
}
