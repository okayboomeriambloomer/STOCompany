package ru.bdproject.STO.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bdproject.STO.models.Person;
import ru.bdproject.STO.models.UpcomingService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class PersonDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Person> getAll() {

        List<Person> list = entityManager.createQuery("FROM Person", Person.class).getResultList();
        return list;
    }

    @Transactional
    public Person saveIfNotExist(Person person) {
        Optional<Person> optionalPerson = getIfExist(person);
        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        }
        entityManager.persist(person); // тут по идее должен проинициализироваться id
        return person;
    }


    private Optional<Person> getIfExist(Person person) {
        List<Person> list = entityManager.createQuery("FROM Person p WHERE name = :name" +
                        " AND surname = :surname AND numberPhone = :number", Person.class)
                .setParameter("name", person.getName())
                .setParameter("surname", person.getSurname())
                .setParameter("number", person.getNumberPhone())
                .getResultList();
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.get(0));
    }

    @Transactional
    public void saveNewUpcomingService(Person person, UpcomingService upcomingService) {
        if (person.getUpcomingServices() == null) {
            person.setUpcomingServices(Collections.singletonList(upcomingService));
        } else {
            person.getUpcomingServices().add(upcomingService);
        }
    }
}
