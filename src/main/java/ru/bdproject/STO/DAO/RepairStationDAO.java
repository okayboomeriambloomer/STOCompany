package ru.bdproject.STO.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bdproject.STO.models.RepairStation;
import ru.bdproject.STO.models.UpcomingService;

import java.util.Collections;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RepairStationDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<RepairStation> getAll() {
        return entityManager.createQuery("SELECT id, address, managerName, numberPhone FROM RepairStation", RepairStation.class).getResultList();
    }

    public RepairStation get(Integer repairStationId) {
        return entityManager.createQuery("SELECT id, address, managerName, numberPhone FROM RepairStation WHERE id = ?1",
                RepairStation.class)
                .setParameter(1, repairStationId)
                .getSingleResult();
    }

    @Transactional
    public void saveNewUpcomingService(RepairStation repairStation, UpcomingService upcomingService) {
        if (repairStation.getUpcomingServices() == null) {
            repairStation.setUpcomingServices(Collections.singletonList(upcomingService));
        } else {
            repairStation.getUpcomingServices().add(upcomingService);
        }
    }

    @Transactional
    public void update(RepairStation repairStation) {
        entityManager.merge(repairStation);
    }

    @Transactional
    public void remove(Integer repairStationId) {
        RepairStation repairStation = entityManager.find(RepairStation.class, repairStationId);
        entityManager.remove(repairStation);
    }

    public void add(RepairStation repairStation) {
        entityManager.persist(repairStation);
    }

    public boolean contains(RepairStation repairStation) {
        List<RepairStation> repairStationList = entityManager.createQuery("FROM RepairStation WHERE address = ?1", RepairStation.class)
                .setParameter(1, repairStation.getAddress())
                .getResultList();
        return !repairStationList.isEmpty();
    }
}
