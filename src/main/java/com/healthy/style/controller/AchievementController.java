package com.healthy.style.controller;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;
import com.healthy.style.service.AchievementService;
import com.healthy.style.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    public ResponseEntity<?> getAchievementById(@PathVariable final Long id) {
        Optional<Achievement> achievement = achievementService.getById(id);
        return achievement.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/achievements/{id:\\d+}/users")
    public List<User> getUsersByAchievement(@PathVariable final Long id) {
        Achievement achievement = achievementService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity: " + Achievement.class.getName() +
                        " with id: " + id + " not found"));
        return userService.getUsersByAchievement(achievement);
    }

    @PostMapping("/achievements")
    public ResponseEntity<Achievement> createAchievement(@Valid @RequestBody final Achievement achievement)
            throws URISyntaxException {
        Achievement createdAchievement = achievementService.save(achievement);
        return ResponseEntity.created(new URI("api/achievements/" + createdAchievement.getId()))
                .body(createdAchievement);
    }

    @PutMapping("/achievements")
    public ResponseEntity<Achievement> updateAchievement(@Valid @RequestBody final Achievement achievement) {
        Achievement createdAchievement = achievementService.save(achievement);
        return ResponseEntity.ok().body(createdAchievement);
    }

    @DeleteMapping("/achievements/{id:\\d+}")
    public ResponseEntity<?> deleteAchievement(@PathVariable final Long id) {
        achievementService.delete(id);
        return ResponseEntity.ok().build();
    }

}
