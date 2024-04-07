package ru.bdproject.STO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.bdproject.STO.models.Car;
import ru.bdproject.STO.models.Person;

@SpringBootApplication
public class StoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoApplication.class, args);

	}

}
