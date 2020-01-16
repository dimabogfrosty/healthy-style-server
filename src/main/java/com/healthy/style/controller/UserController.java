package com.healthy.style.controller;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Record;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.report.Report;
import com.healthy.style.service.*;
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
    private ReportService reportService;

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

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
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

    @GetMapping("/users/{id:\\d+}/report")
    public Report createUserReport(@PathVariable final Long id) {
        return reportService.createUserReport(userService.getById(id));
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
