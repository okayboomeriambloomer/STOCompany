package ru.bdproject.STO.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bdproject.STO.models.Admin;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class AdminDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Admin> findByLogin(String login) {
        List<Admin> list = entityManager.createQuery("FROM Admin WHERE login = ?1", Admin.class)
                .setParameter(1, login)
                .getResultList();
        if (list.isEmpty()) return Optional.empty();
        else return Optional.of(list.get(0));
    }

}
