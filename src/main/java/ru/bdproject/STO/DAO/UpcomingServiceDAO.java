package ru.bdproject.STO.DAO;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bdproject.STO.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UpcomingServiceDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public UpcomingService saveNewService(Person person,
                                          Car car,
                                          RepairStation repairStation,
                                          TypeOfRepair typeOfRepair,
                                          LocalDateTime localDateTime) {
        UpcomingService upcomingService = new UpcomingService(repairStation, typeOfRepair, person, car, localDateTime);
        entityManager.persist(upcomingService);
        return upcomingService;
    }

    public List<UpcomingService> getAll() {
        return entityManager
                .createQuery("FROM UpcomingService ORDER BY date", UpcomingService.class)
                .getResultList();
    }

    @Transactional
    public void replaceServiceToHistory(Integer id) {
        UpcomingService upcomingService = entityManager
                .createQuery("FROM UpcomingService WHERE id = ?1", UpcomingService.class)
                .setParameter(1, id)
                .getSingleResult();
        HistoryOfService newHistory = HistoryOfService.of(upcomingService);
        entityManager.persist(newHistory);
        entityManager.remove(upcomingService);
    }
}
