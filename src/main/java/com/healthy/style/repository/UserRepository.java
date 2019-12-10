package com.healthy.style.repository;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findUsersByGender(User.Gender gender);

    List<User> findUsersByRoles(Role role);

    List<User> findUsersByAchievements(Achievement achievement);

}
