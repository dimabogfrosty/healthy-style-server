package com.healthy.style.controller;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Record;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.report.DateReport;
import com.healthy.style.report.Report;
import com.healthy.style.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final AchievementService achievementService;
    private final RecordService recordService;
    private final ReportService reportService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{id:\\d+}")
    public ResponseEntity<?> getUserById(@PathVariable final Long id) {
        Optional<User> user = userService.getById(id);
        return user.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/users/{id:\\d+}/roles")
    public List<Role> getRolesByUser(@PathVariable final Long id) {
        User user = userService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity: " + User.class.getName() +
                        " with id: " + id + " not found"));
        return roleService.getRolesByUser(user);
    }

    @GetMapping("/users/{id:\\d+}/achievements")
    public List<Achievement> getAchievementsByUser(@PathVariable final Long id) {
        User user = userService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity: " + User.class.getName() +
                        " with id: " + id + " not found"));
        return achievementService.getAchievementsByUser(user);
    }

    @GetMapping("/users/{id:\\d+}/records")
    public List<Record> getRecordsByUser(@PathVariable final Long id) {
        User user = userService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity: " + User.class.getName() +
                        " with id: " + id + " not found"));
        return recordService.getUserRecords(user.getId());
    }

    @GetMapping("/users/{id:\\d+}/report")
    public Report createUserReportBetweenRunDate(@PathVariable final Long id,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DATE) final LocalDate startDate,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DATE) final LocalDate endDate) {
        User user = userService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity: " + User.class.getName() +
                        " with id: " + id + " not found"));

        if (startDate != null && endDate != null) {
            return reportService.createUserReportBetweenRunDate(user.getId(), startDate, endDate);
        } else {
            return reportService.createUserReport(user.getId());
        }
    }

    @GetMapping("/users/{id:\\d+}/weekReport")
    public List<DateReport> createUserReportByWeek(@PathVariable final Long id) {
        return reportService.createUserReportByWeek(id);
    }

    @GetMapping("users/{id:\\d+}/monthReport")
    public List<DateReport> createUserReportByMonth(@PathVariable final Long id) {
        return reportService.createUserReportByMonth(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody final User user) throws URISyntaxException {
        User savedUser = userService.save(user);
        return ResponseEntity.created(new URI("/api/users/" + savedUser.getId())).body(savedUser);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody final User user) {
        User updatedUser = userService.save(user);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/users/{id:\\d+}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
