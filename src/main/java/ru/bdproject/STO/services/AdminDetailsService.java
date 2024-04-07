package ru.bdproject.STO.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bdproject.STO.DAO.AdminDAO;
import ru.bdproject.STO.models.Admin;
import ru.bdproject.STO.security.AdminDetails;

import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService {
    private AdminDAO adminDAO;

    @Autowired
    public AdminDetailsService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminDAO.findByLogin(username);
        if (admin.isEmpty()) throw new UsernameNotFoundException("Пользователь с таким логином не найден");
        return new AdminDetails(admin.get());
    }
}
