package com.healthy.style.service.impl;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.entity.User.Gender;
import com.healthy.style.repository.UserRepository;
import com.healthy.style.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getByGender(final Gender gender) {
        return userRepository.findUsersByGender(gender);
    }

    @Override
    public List<User> getUsersByRole(final Role role) {
        return userRepository.findUsersByRoles(role);
    }

    @Override
    public List<User> getUsersByAchievement(final Achievement achievement) {
        return userRepository.findUsersByAchievements(achievement);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(final Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User save(final User entity) {
        return userRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(final Long id) {
        throw new UnsupportedOperationException("Can't delete user for table!");
    }

}
