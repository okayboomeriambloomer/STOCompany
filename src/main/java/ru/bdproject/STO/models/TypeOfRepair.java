package ru.bdproject.STO.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Entity
@Table(name = "type_of_repair", schema = "sto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfRepair {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Название услуги не заполнено")
    private String name;

    @Column(name = "price")
    @NotNull(message = "Стоимость услуги не заполнена")
    private double price;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "typeOfRepair", cascade = CascadeType.REMOVE)
    private List<HistoryOfService> historyTypeOfRepair;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "typeOfRepair", cascade = CascadeType.REMOVE)
    private List<UpcomingService> upcomingServices;

    public TypeOfRepair(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public TypeOfRepair(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
