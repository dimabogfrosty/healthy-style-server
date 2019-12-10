package com.healthy.style.repository;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.User;
import com.healthy.style.repository.annotation.RepositoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@RepositoryTest
public class AchievementRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AchievementRepository achievementRepository;

    private User userDina;
    private User userVlad;
    private Achievement achievementGood;
    private Achievement achievementGreat;

    @Before
    public void setUp() {
        userDina = new User("dinaa", "dddddd", "dina@email.com");
        userVlad = new User("vladis", "vvvvvvvv", "vlad@email.com");

        achievementGood = new Achievement("Good", "image.png");
        achievementGood.setUsers(new ArrayList<>(singletonList(userDina)));
        entityManager.persistAndFlush(achievementGood);

        achievementGreat = new Achievement("Well", "image.png");
        achievementGreat.setUsers(new ArrayList<>(asList(userDina, userVlad)));
        entityManager.persistAndFlush(achievementGreat);

        userDina.setAchievements(new ArrayList<>(asList(achievementGood, achievementGreat)));
        entityManager.persistAndFlush(userDina);

        userVlad.setAchievements(new ArrayList<>(singletonList(achievementGreat)));
        entityManager.persistAndFlush(userVlad);
    }

    @Test
    public void whenFindByName_thenReturnAchievement() {
        Achievement actualAchievement = achievementRepository.findByName(achievementGood.getName());

        assertThat(actualAchievement.getName(), is(achievementGood.getName()));
    }

    @Test
    public void whenFindByName_thenReturnAchievementWithUsers() {
        Achievement actualAchievement = achievementRepository.findByName(achievementGreat.getName());

        assertThat(actualAchievement.getName(), is(achievementGreat.getName()));
        assertThat(actualAchievement.getUsers(), hasItems(userDina, userVlad));
    }

    @Test
    public void whenFindAchievementsByUsers_thenReturnAchievementsThisUser() {
        List<Achievement> achievements = achievementRepository.findAchievementsByUsers(userDina);

        assertThat(achievements.stream().map(Achievement::getName).collect(toList()), hasItems("Good", "Well"));
    }

    @Test
    public void whenCreateAchievement_thenReturnCreatedAchievement() {
        Achievement expectedAchievement = new Achievement("Good job", "image");

        Achievement actualAchievement = achievementRepository.saveAndFlush(expectedAchievement);

        assertThat(actualAchievement.getName(), is(expectedAchievement.getName()));
    }

    @Test
    public void whenCreateAchievement_thenReturnCreatedAchievementWithUser() {
        Achievement expectedAchievement = new Achievement("Good job", "image");
        expectedAchievement.setUsers(singletonList(userVlad));

        Achievement actualAchievement = achievementRepository.saveAndFlush(expectedAchievement);

        assertThat(actualAchievement.getName(), is(expectedAchievement.getName()));
        assertThat(actualAchievement.getUsers(), is(expectedAchievement.getUsers()));
    }

    @Test
    public void whenUpdateAchievement_thenReturnUpdatedAchievement() {
        achievementGreat.setDescription("Really really good!");

        Achievement actualAchievement = achievementRepository.saveAndFlush(achievementGreat);

        assertThat(actualAchievement.getName(), is(achievementGreat.getName()));
        assertThat(actualAchievement.getDescription(), is(achievementGreat.getDescription()));
    }

    @Test
    public void whenUpdateAchievement_thenReturnUpdatedAchievementWhitUser() {
        Achievement expectedAchievement = new Achievement("You're the best", "image.png");
        expectedAchievement.setUsers(new ArrayList<>(singletonList(userDina)));
        entityManager.persistAndFlush(expectedAchievement);
        expectedAchievement.getUsers().add(userVlad);

        Achievement actualAchievement = achievementRepository.saveAndFlush(expectedAchievement);

        assertThat(actualAchievement.getName(), is(expectedAchievement.getName()));
        assertThat(actualAchievement.getUsers(), is(expectedAchievement.getUsers()));
    }

    @Test
    public void whenDeleteAchievement_thenDeletingShouldBeSuccessful() {
        achievementRepository.delete(achievementGreat);

        assertThat(achievementRepository.count(), is(1L));
    }

    @Test(expected = PersistenceException.class)
    public void whenCreateAchievementWithDuplicateName_thenThrowException() {
        entityManager.persistAndFlush(new Achievement("Well", "image1.png"));
        entityManager.persistAndFlush(new Achievement("Well", "image1.png"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenCreateRoleWithNameLengthMore30_thenThrowException() {
        entityManager.persistAndFlush(
                new Achievement("AchievementAchievementAchievementAchievementAchievement", "image.png"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenCreateRoleWithNameLengthLess3_thenThrowException() {
        entityManager.persistAndFlush(new Achievement("Ac", "image.png"));
    }

    @Test(expected = PersistenceException.class)
    public void whenCreateRoleWithNullName_thenThrowException() {
        entityManager.persistAndFlush(new Achievement());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }
}
