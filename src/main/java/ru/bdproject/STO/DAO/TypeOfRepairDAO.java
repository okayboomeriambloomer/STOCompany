package ru.bdproject.STO.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.bdproject.STO.models.TypeOfRepair;
import ru.bdproject.STO.models.UpcomingService;

import java.util.Collections;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class TypeOfRepairDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<TypeOfRepair> getAll() {
        return entityManager.createQuery("SELECT id, name, price FROM TypeOfRepair", TypeOfRepair.class).getResultList();
    }

    public TypeOfRepair get(Integer typeOfRepairId) {
        return entityManager.createQuery("SELECT id, name, price FROM TypeOfRepair WHERE id = ?1",
                        TypeOfRepair.class)
                .setParameter(1, typeOfRepairId)
                .getSingleResult();
    }

    @Transactional
    public void saveNewUpcomingService(TypeOfRepair typeOfRepair, UpcomingService upcomingService) {
        if (typeOfRepair.getUpcomingServices() == null) {
            typeOfRepair.setUpcomingServices(Collections.singletonList(upcomingService));
        } else {
            typeOfRepair.getUpcomingServices().add(upcomingService);
        }
    }

    @Transactional
    public void add(TypeOfRepair typeOfRepair) {
        entityManager.persist(typeOfRepair);
    }

    @Transactional
    public void remove(Integer typeOfRepairId) {
        TypeOfRepair typeOfRepair = entityManager.find(TypeOfRepair.class, typeOfRepairId);
        entityManager.remove(typeOfRepair);
    }

    @Transactional
    public void update(TypeOfRepair typeOfRepair) {
        entityManager.merge(typeOfRepair);
    }

    public boolean contains(TypeOfRepair typeOfRepair) {
         List<TypeOfRepair> typeOfRepairList = entityManager.createQuery("SELECT name FROM TypeOfRepair WHERE name=?1", TypeOfRepair.class)
                .setParameter(1, typeOfRepair.getName())
                .getResultList();
         return !typeOfRepairList.isEmpty();
    }
}
