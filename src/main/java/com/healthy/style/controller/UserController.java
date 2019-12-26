package com.healthy.style.controller;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Record;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.service.AchievementService;
import com.healthy.style.service.RecordService;
import com.healthy.style.service.RoleService;
import com.healthy.style.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    private AchievementService achievementService;
    private RecordService recordService;

    @Autowired
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(final RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setAchievementService(final AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @Autowired
    public void setRecordService(final RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{id:\\d+}")
    public User getUserById(@PathVariable final Long id) {
        return userService.getById(id);
    }

    @GetMapping("/users/{id:\\d+}/roles")
    public List<Role> getRolesByUser(@PathVariable final Long id) {
        return roleService.getRolesByUser(userService.getById(id));
    }

    @GetMapping("/users/{id:\\d+}/achievements")
    public List<Achievement> getAchievementsByUser(@PathVariable final Long id) {
        return achievementService.getAchievementsByUser(userService.getById(id));
    }

    @GetMapping("/users/{id:\\d+}/records")
    public List<Record> getRecordsByUser(@PathVariable final Long id) {
        return recordService.getUserRecords(userService.getById(id));
    }

    @PostMapping("/users")
    public User createUser(@RequestBody final User user) {
        return userService.save(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody final User user) {
        return userService.save(user);
    }

    @DeleteMapping("/users/{id:\\d+}")
    public void deleteUser(@PathVariable final Long id) {
        userService.delete(id);
    }

}
