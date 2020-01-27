package com.healthy.style.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "achievements")
public class Achievement implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "achievement_name", unique = true)
    @NotBlank(message = "Achievement name can't be null")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Achievement description can't be null")
    private String description;

    @Column(name = "image_src")
    @NotBlank(message = "Achievement image can't be null")
    private String image;

    @ManyToMany(mappedBy = "achievements")
    @JsonBackReference
    private List<User> users = new ArrayList<>();

}
