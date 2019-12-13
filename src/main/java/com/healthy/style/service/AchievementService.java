package com.healthy.style.service;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;

import java.util.List;

public interface AchievementService extends EntityService<Achievement> {

    Achievement getByName(String name);

    List<Achievement> getAchievementsByUser(User user);

}
