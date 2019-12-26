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
    public void setAchievementRepository(final AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public Achievement getByName(final String name) {
        return achievementRepository.findByName(name);
    }

    @Override
    public List<Achievement> getAchievementsByUser(final User user) {
        return achievementRepository.findAchievementsByUsers(user);
    }

    @Override
    public List<Achievement> getAll() {
        return achievementRepository.findAll();
    }

    @Override
    public Achievement getById(final Long id) {
        return achievementRepository.getOne(id);
    }

    @Override
    public Achievement save(final Achievement entity) {
        return achievementRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(final Long id) {
        achievementRepository.deleteById(id);
    }

}
