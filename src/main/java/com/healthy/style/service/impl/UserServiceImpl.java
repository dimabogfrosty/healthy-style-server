package com.healthy.style.service.impl;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.entity.User.Gender;
import com.healthy.style.repository.UserRepository;
import com.healthy.style.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getByGender(Gender gender) {
        return userRepository.findUsersByGender(gender);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findUsersByRoles(role);
    }

    @Override
    public List<User> getUsersByAchievement(Achievement achievement) {
        return userRepository.findUsersByAchievements(achievement);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User save(User entity) {
        return userRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Can't delete user for table!");
    }

}
