package ru.bdproject.STO.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "repair_station", schema = "sto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairStation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "address")
    @NotEmpty(message = "Адрес не заполнен")
    private String address;

    @Column(name = "manager_name")
    @NotEmpty(message = "Имя управляющего не заполнено")
    @Pattern(regexp = "[а-яА-Яa-zA-Z]+", message = "Формат имени невалиден")
    private String managerName;

    @Column(name = "number_phone")
    @Pattern(regexp = "(\\+7|8)[0-9]{10}", message = "Формат номера телефона невалиден")
    @NotEmpty(message = "Номер телефона не заполнен")
    private String numberPhone;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "repairStation", cascade = CascadeType.REMOVE)
    private List<HistoryOfService> historyServices;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "repairStation", cascade = CascadeType.REMOVE)
    private List<UpcomingService> upcomingServices;

    public RepairStation(String address, String managerName, String numberPhone) {
        this.address = address;
        this.managerName = managerName;
        this.numberPhone = numberPhone;
    }

    public RepairStation(Integer id, String address, String managerName, String numberPhone) {
        this.id = id;
        this.address = address;
        this.managerName = managerName;
        this.numberPhone = numberPhone;
    }
}
