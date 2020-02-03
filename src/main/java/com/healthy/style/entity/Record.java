package com.healthy.style.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "records")
public class Record implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_date", nullable = false)
    private LocalDate runDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public LocalTime getRunningTime() {
        return LocalTime.ofNanoOfDay(endTime.toNanoOfDay() - startTime.toNanoOfDay());
    }

    @Override
    public String toString() {
        return "Record[" +
                "id=" + id +
                ", runDate=" + runDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", distance=" + distance +
                ']';
    }
}
