package com.healthy.style.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;

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

    public User(final String username, final String password, final String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setWeight(final Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setHeight(final Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    public void setRoles(final List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setAchievements(final List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setRecords(final List<Record> records) {
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public enum Gender {
        MALE("Male"),
        FEMALE("Female");

        private final String gender;

        Gender(final String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return gender;
        }
    }

}
