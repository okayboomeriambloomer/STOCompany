package ru.bdproject.STO.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bdproject.STO.models.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class CarDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Car> getAllCar() {

        List<Car> list = entityManager.createQuery("FROM Car", Car.class).getResultList();
        return list;
    }

    @Transactional
    public void testCar() {
        Car car = entityManager.find(Car.class, 1);
        RepairStation repairStation = entityManager.find(RepairStation.class, 1);
        TypeOfRepair typeOfRepair = entityManager.find(TypeOfRepair.class, 1);

        Person tanya = new Person("Tanya", "Ershova", "81231231212");

        System.out.println(entityManager.createQuery("FROM Person p WHERE name = :name" +
                " AND surname = :surname AND numberPhone = :number")
                .setParameter("name", tanya.getName())
                .setParameter("surname", tanya.getSurname())
                .setParameter("number", tanya.getNumberPhone())
                .getResultList().size());

    }

    @Transactional
    public void saveNewUpcomingService(Car car, UpcomingService upcomingService) {
        if (car.getUpcomingServices() == null) {
            car.setUpcomingServices(Collections.singletonList(upcomingService));
        } else {
            car.getUpcomingServices().add(upcomingService);
        }
    }

    @Transactional
    public Car saveIfNotExist(Car car) {
        Optional<Car> optionalPerson = getIfExist(car);
        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        }
        entityManager.persist(car); // тут по идее должен проинициализироваться id
        return car;
    }


    private Optional<Car> getIfExist(Car car) {
        List<Car> list = entityManager.createQuery("FROM Car c WHERE brand = :brand" +
                        " AND govNumber = :govNumber AND year = :year", Car.class)
                .setParameter("brand", car.getBrand())
                .setParameter("govNumber", car.getGovNumber())
                .setParameter("year", car.getYear())
                .getResultList();
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.get(0));
    }
}
