package com.healthy.style.service;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;
import com.healthy.style.repository.AchievementRepository;
import com.healthy.style.service.impl.AchievementServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AchievementServiceTest {

    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementServiceImpl achievementService;

    private User userDina;
    private Achievement achievementGood;
    private Achievement achievementGreat;

    @Before
    public void setUp() {
        userDina = new User();
        userDina.setUsername("dinaa");
        userDina.setPassword("dddddd");
        userDina.setEmail("dina@email.com");

        User userVlad = new User();
        userVlad.setUsername("vladis");
        userVlad.setPassword("vvvvvvvv");
        userVlad.setEmail("vlad@email.com");

        achievementGood = new Achievement();
        achievementGood.setId(1L);
        achievementGood.setName("Good");
        achievementGood.setImage("image.png");
        achievementGood.setUsers(new ArrayList<>(singletonList(userDina)));

        achievementGreat = new Achievement();
        achievementGreat.setId(1L);
        achievementGreat.setName("Well");
        achievementGreat.setDescription("It's good!");
        achievementGreat.setImage("image.png");
        achievementGreat.setUsers(new ArrayList<>(asList(userDina, userVlad)));

        userDina.setAchievements(new ArrayList<>(asList(achievementGood, achievementGreat)));

        userVlad.setAchievements(new ArrayList<>(singletonList(achievementGreat)));
    }

    @Test
    public void whenGetAll_thenReturnAchievements() {
        when(achievementRepository.findAll()).thenReturn(asList(achievementGood, achievementGreat));

        List<Achievement> achievements = achievementService.getAll();

        assertThat(achievements, hasSize(2));
        assertThat(achievements, hasItems(achievementGood, achievementGreat));
        verify(achievementRepository, times(1)).findAll();
    }

    @Test
    public void whenGetById_thenReturnAchievement() {
        when(achievementRepository.getOne(achievementGreat.getId())).thenReturn(achievementGreat);

        Achievement actualAchievement = achievementService.getById(achievementGreat.getId());

        assertThat(actualAchievement.getId(), is(actualAchievement.getId()));
        verify(achievementRepository, timeout(1)).getOne(achievementGreat.getId());
    }

    @Test
    public void whenGetByName_thenReturnAchievement() {
        when(achievementRepository.findByName(achievementGood.getName())).thenReturn(achievementGood);

        Achievement actualAchievement = achievementService.getByName(achievementGood.getName());

        assertThat(actualAchievement.getName(), is(achievementGood.getName()));
        verify(achievementRepository, timeout(1)).findByName(achievementGood.getName());
    }

    @Test
    public void whenGetAchievementsByUser_thenReturnAchievements() {
        when(achievementRepository.findAchievementsByUsers(userDina))
                .thenReturn(asList(achievementGreat, achievementGood));

        List<Achievement> achievements = achievementService.getAchievementsByUser(userDina);

        assertThat(achievements, hasSize(2));
        assertThat(achievements, hasItems(achievementGood, achievementGreat));
        verify(achievementRepository, timeout(1)).findAchievementsByUsers(userDina);
    }

    @Test
    public void whenSaveAchievement_thenReturnAchievement() {
        when(achievementRepository.saveAndFlush(any(Achievement.class))).thenReturn(new Achievement());

        Achievement actualAchievement = achievementService.save(achievementGood);

        assertThat(actualAchievement, instanceOf(Achievement.class));
        verify(achievementRepository, times(1)).saveAndFlush(achievementGood);
    }

    @Test
    public void whenDeleteRole_thenDeletingShouldBeSuccessful() {
        achievementService.delete(achievementGood.getId());

        verify(achievementRepository, times(1)).deleteById(achievementGood.getId());
    }
}
