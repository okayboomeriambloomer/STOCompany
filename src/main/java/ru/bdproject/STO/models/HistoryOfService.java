package ru.bdproject.STO.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(schema = "sto", name = "history_of_service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryOfService {
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

    public HistoryOfService(RepairStation repairStation, TypeOfRepair typeOfRepair, Person person, Car car, LocalDateTime date) {
        this.repairStation = repairStation;
        this.typeOfRepair = typeOfRepair;
        this.person = person;
        this.car = car;
        this.date = date;
    }

    public static HistoryOfService of(UpcomingService oldService) {
        return new HistoryOfService(
                oldService.getRepairStation(),
                oldService.getTypeOfRepair(),
                oldService.getPerson(),
                oldService.getCar(),
                oldService.getDate()
        );
    }
}
