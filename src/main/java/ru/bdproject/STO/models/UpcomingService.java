package ru.bdproject.STO.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(schema = "sto", name = "upcoming_service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingService {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_repair_station", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RepairStation repairStation;

    @ManyToOne
    @JoinColumn(name = "id_type_of_repair", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TypeOfRepair typeOfRepair;

    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

    @ManyToOne
    @JoinColumn(name = "id_car", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Car car;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    public UpcomingService(RepairStation repairStation, TypeOfRepair typeOfRepair, Person person, Car car, LocalDateTime date) {
        this.repairStation = repairStation;
        this.typeOfRepair = typeOfRepair;
        this.person = person;
        this.car = car;
        this.date = date;
    }

    public static UpcomingService of(HistoryOfService oldService) {
        return new UpcomingService(
                oldService.getRepairStation(),
                oldService.getTypeOfRepair(),
                oldService.getPerson(),
                oldService.getCar(),
                oldService.getDate()
        );
    }
}