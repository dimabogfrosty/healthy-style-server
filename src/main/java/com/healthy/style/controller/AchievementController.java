package com.healthy.style.controller;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;
import com.healthy.style.service.AchievementService;
import com.healthy.style.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AchievementController {

    private AchievementService achievementService;
    private UserService userService;

    @Autowired
    public void setAchievementService(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/achievements")
    public List<Achievement> getAllAchievements() {
        return achievementService.getAll();
    }

    @GetMapping("/achievements/{id:\\d+}")
    public Achievement getAchievementById(@PathVariable Long id) {
        return achievementService.getById(id);
    }

    @GetMapping("/achievements/{id:\\d+}/users")
    public List<User> getUsersByAchievement(@PathVariable Long id) {
        return userService.getUsersByAchievement(achievementService.getById(id));
    }

    @PostMapping("/achievements")
    public Achievement createAchievement(@RequestBody Achievement achievement) {
        return achievementService.save(achievement);
    }

    @PutMapping("/achievements")
    public Achievement updateAchievement(@RequestBody Achievement achievement) {
        return achievementService.save(achievement);
    }

    @DeleteMapping("/achievements/{id:\\d+}")
    public void deleteAchievement(@PathVariable Long id) {
        achievementService.delete(id);
    }

}
