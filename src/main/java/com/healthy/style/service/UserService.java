package com.healthy.style.service;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.entity.User.Gender;

import java.util.List;

public interface UserService extends EntityService<User> {

    User getByUsername(String username);

    User getByEmail(String email);

    List<User> getByGender(Gender gender);

    List<User> getUsersByRole(Role role);

    List<User> getUsersByAchievement(Achievement achievement);

}
