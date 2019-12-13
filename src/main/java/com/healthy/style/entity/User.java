package com.healthy.style.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

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

    @ManyToMany(mappedBy = "users")
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Achievement> achievements = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    public enum Gender {
        MALE("Male"),
        FEMALE("Female");

        private String gender;

        Gender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return gender;
        }
    }

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
