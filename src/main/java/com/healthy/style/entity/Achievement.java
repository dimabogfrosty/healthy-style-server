package com.healthy.style.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "achievements")
public class Achievement implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "achievements_id_seq")
    @SequenceGenerator(name = "achievements_id_seq", sequenceName = "achievements_id_seq", allocationSize = 1)
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

    public Achievement() {
    }

    public Achievement(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
