package ru.bdproject.STO.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "car", schema = "sto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand")
    @NotEmpty(message = "Марка машины пуста")
    @Pattern(regexp = "[а-яА-Яa-zA-Z]+", message = "Формат марки машины невалиден")
    private String brand;

    @Column(name = "gov_number")
    @NotEmpty(message = "Гос. номер машины пуст")
    @Pattern(regexp = "[А-Я][0-9]{3}[А-Я]{2}[0-9]{3}", message = "Формат гос. номера невалиден")
    private String govNumber;

    @Column(name = "year")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy")
    @NotNull(message = "Год выпуска пуст")
    private Date year;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "car")
    private List<HistoryOfService> historyOfServices;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "car")
    private List<UpcomingService> upcomingServices;

    public Car(String brand, String govNumber, Date year) {
        this.brand = brand;
        this.govNumber = govNumber;
        this.year = year;
    }
}
