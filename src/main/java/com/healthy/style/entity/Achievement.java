package com.healthy.style.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "achievements")
public class Achievement implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "achievement_name", nullable = false, unique = true)
    @Size(min = 3, max = 30)
    private String name;

    @Column(name = "description")
    @Size(max = 255)
    private String description;

    @Column(name = "image_src", nullable = false)
    @Size(max = 255)
    private String image;

    @ManyToMany(mappedBy = "achievements")
    @JsonBackReference
    private List<User> users = new ArrayList<>();

}
