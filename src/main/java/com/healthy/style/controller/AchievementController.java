package com.healthy.style.controller;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;
import com.healthy.style.service.AchievementService;
import com.healthy.style.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;
    private final UserService userService;

    @GetMapping("/achievements")
    public List<Achievement> getAllAchievements() {
        return achievementService.getAll();
    }

    @GetMapping("/achievements/{id:\\d+}")
    public Achievement getAchievementById(@PathVariable final Long id) {
        return achievementService.getById(id);
    }

    @GetMapping("/achievements/{id:\\d+}/users")
    public List<User> getUsersByAchievement(@PathVariable final Long id) {
        return userService.getUsersByAchievement(achievementService.getById(id));
    }

    @PostMapping("/achievements")
    public Achievement createAchievement(@RequestBody final Achievement achievement) {
        return achievementService.save(achievement);
    }

    @PutMapping("/achievements")
    public Achievement updateAchievement(@RequestBody final Achievement achievement) {
        return achievementService.save(achievement);
    }

    @DeleteMapping("/achievements/{id:\\d+}")
    public void deleteAchievement(@PathVariable final Long id) {
        achievementService.delete(id);
    }

}
