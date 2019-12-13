package com.healthy.style.service.impl;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;
import com.healthy.style.repository.AchievementRepository;
import com.healthy.style.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AchievementServiceImpl implements AchievementService {

    private AchievementRepository achievementRepository;

    @Autowired
    public void setAchievementRepository(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public Achievement getByName(String name) {
        return achievementRepository.findByName(name);
    }

    @Override
    public List<Achievement> getAchievementsByUser(User user) {
        return achievementRepository.findAchievementsByUsers(user);
    }

    @Override
    public List<Achievement> getAll() {
        return achievementRepository.findAll();
    }

    @Override
    public Achievement getById(Long id) {
        return achievementRepository.getOne(id);
    }

    @Override
    public Achievement save(Achievement entity) {
        return achievementRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(Long id) {
        achievementRepository.deleteById(id);
    }

}
