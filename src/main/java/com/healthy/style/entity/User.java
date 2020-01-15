package com.healthy.style.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

@Data
@Entity
@Table(name = "users")
public class User implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    @Size(min = 5, max = 26)
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 6, max = 50)
    private String password;

    @Column(name = "first_name")
    @Size(min = 3, max = 15)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3, max = 20)
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    @Size(min = 5, max = 50)
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Integer height;

    @ManyToMany(cascade = {PERSIST})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(cascade = {PERSIST})
    @JoinTable(
            name = "users_achievements",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private List<Achievement> achievements = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    public enum Gender {
        MALE,
        FEMALE
    }

}
