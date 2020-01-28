package com.healthy.style.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Column(name = "user_name", unique = true)
    @NotBlank(message = "Username can't be blank")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Password can't be blank")
    private String password;

    @Column(name = "first_name")
    @NotBlank(message = "Firstname can't be blank")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Lastname can't be blank")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email can't be blank")
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Integer height;

    @ManyToMany(cascade = {PERSIST}, fetch = FetchType.EAGER)
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
    
    public User() {
        
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.birthDate = user.birthDate;
        this.email = user.email;
        this.gender = user.gender;
        this.weight = user.weight;
        this.height = user.height;
        this.roles = user.roles;
        this.achievements = user.achievements;
        this.records = user.records;
    }

    public enum Gender {
        MALE,
        FEMALE
    }

}
