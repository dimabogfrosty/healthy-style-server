package com.healthy.style.repository;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Achievement findByName(String name);

    List<Achievement> findAchievementsByUsers(User user);

}
