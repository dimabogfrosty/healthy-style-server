package com.healthy.style.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "records")
public class Record implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_date")
    @NotNull(message = "Rundate can't be null")
    private LocalDate runDate;

    @Column(name = "start_time")
    @NotNull(message = "Start time can't be null")
    private LocalTime startTime;

    @Column(name = "end_time")
    @NotNull(message = "End time can't be null")
    private LocalTime endTime;

    @Column(name = "distance")
    @NotNull(message = "Distance can't be null")
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public LocalTime getRunningTime() {
        return LocalTime.ofNanoOfDay(endTime.toNanoOfDay() - startTime.toNanoOfDay());
    }

}
