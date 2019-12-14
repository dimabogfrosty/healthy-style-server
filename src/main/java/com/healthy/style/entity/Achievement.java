package com.healthy.style.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "achievements")
public class Achievement extends AbstractEntity {

    @Column(name = "achievement_name", nullable = false, unique = true)
    @Size(min = 3, max = 30)
    private String name;

    @Column(name = "description")
    @Size(max = 255)
    private String description;

    @Column(name = "image_src", nullable = false)
    @Size(max = 255)
    private String image;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_achievements",
            joinColumns = {@JoinColumn(name = "achievement_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonBackReference
    private List<User> users = new ArrayList<>();

    public Achievement() {
    }

    public Achievement(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
