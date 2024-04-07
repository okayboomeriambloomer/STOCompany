package ru.bdproject.STO.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "Person", schema = "sto")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не заполнено")
    @Column(name = "name")
    @Pattern(regexp = "[а-яА-Яa-zA-Z]+", message = "Формат имени невалиден")
    private String name;

    @NotEmpty(message = "Фамилия не заполнена")
    @Column(name = "surname")
    @Pattern(regexp = "[а-яА-Яa-zA-Z]+", message = "Формат фамилии невалиден")
    private String surname;

    @NotEmpty(message = "Номер телефона не заполнен")
    @Column(name = "number_phone")
    @Pattern(regexp="(\\+7|8)[0-9]{10}", message = "Формат номера телефона невалиден")
    private String numberPhone;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "person")
    private List<HistoryOfService> historyOfServices;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "person")
    private List<UpcomingService> upcomingServices;

    public Person(String name, String surname, String numberPhone) {
        this.name = name;
        this.surname = surname;
        this.numberPhone = numberPhone;
    }
}
