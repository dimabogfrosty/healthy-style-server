package com.healthy.style.repository;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Optional<Achievement> findByName(String name);

    List<Achievement> findAchievementsByUsers(User user);

}
