package com.healthy.style.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    @Size(min = 3, max = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<User> users = new ArrayList<>();

}
