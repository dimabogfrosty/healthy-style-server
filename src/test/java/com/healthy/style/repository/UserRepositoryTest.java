package com.healthy.style.repository;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Record;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.repository.annotation.RepositoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.healthy.style.entity.User.Gender.FEMALE;
import static com.healthy.style.entity.User.Gender.MALE;
import static java.time.Month.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@RepositoryTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User userDina;
    private User userVlad;
    private Record record1;
    private Record record2;
    private Record record3;
    private Role roleAdmin;
    private Role roleUser;
    private Achievement achievementGood;
    private Achievement achievementGreat;

    @Before
    public void setUp() {
        userDina = new User("dinaa", "dddddd", "dina@email.com");
        userDina.setFirstName("Dina");
        userDina.setLastName("Evgeni");
        userDina.setBirthDate(LocalDate.of(2000, MAY, 20));
        userDina.setGender(FEMALE);
        userDina.setWeight(58.6);
        userDina.setHeight(160);

        userVlad = new User("vladis", "vvvvvvvv", "vlad@email.com");
        userVlad.setFirstName("Vlad");
        userVlad.setLastName("Kovalok");
        userVlad.setBirthDate(LocalDate.of(1999, APRIL, 24));
        userVlad.setGender(MALE);
        userVlad.setWeight(70.4);
        userVlad.setHeight(180);

        record1 = new Record(
                LocalDate.of(2019, DECEMBER, 1),
                LocalTime.of(2, 24, 0),
                LocalTime.of(3, 58, 0), 2.3D);
        record1.setUser(userDina);
        entityManager.persistAndFlush(record1);

        record2 = new Record(
                LocalDate.of(2019, NOVEMBER, 20),
                LocalTime.of(5, 20, 0),
                LocalTime.of(7, 30, 59), 5.4D);
        record2.setUser(userDina);
        entityManager.persistAndFlush(record2);

        record3 = new Record(
                LocalDate.of(2019, DECEMBER, 15),
                LocalTime.of(15, 24, 0),
                LocalTime.of(17, 58, 0), 3.5D);
        record3.setUser(userVlad);
        entityManager.persistAndFlush(record3);

        roleAdmin = new Role("ADMIN");
        roleAdmin.setUsers(new ArrayList<>(singletonList(userVlad)));
        entityManager.persistAndFlush(roleAdmin);

        roleUser = new Role("USER");
        roleUser.setUsers(new ArrayList<>(asList(userDina, userVlad)));
        entityManager.persistAndFlush(roleUser);

        achievementGood = new Achievement("Good", "image.png");
        achievementGood.setUsers(new ArrayList<>(singletonList(userDina)));
        entityManager.persistAndFlush(achievementGood);

        achievementGreat = new Achievement("Well", "image.png");
        achievementGreat.setUsers(new ArrayList<>(asList(userDina, userVlad)));
        entityManager.persistAndFlush(achievementGreat);

        userDina.setRecords(new ArrayList<>(asList(record1, record2)));
        userDina.setRoles(new ArrayList<>(singletonList(roleUser)));
        userDina.setAchievements(new ArrayList<>(asList(achievementGood, achievementGreat)));
        entityManager.persistAndFlush(userDina);

        userVlad.setRecords(new ArrayList<>(singletonList(record3)));
        userVlad.setRoles(new ArrayList<>(asList(roleAdmin, roleUser)));
        userVlad.setAchievements(new ArrayList<>(singletonList(achievementGreat)));
        entityManager.persistAndFlush(userVlad);
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        User actualUser = userRepository.findByUsername(userDina.getUsername());

        assertThat(actualUser.getUsername(), is(userDina.getUsername()));
        assertThat(actualUser.getFirstName(), is(userDina.getFirstName()));
        assertThat(actualUser.getLastName(), is(userDina.getLastName()));
        assertThat(actualUser.getBirthDate(), is(userDina.getBirthDate()));
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        User actualUser = userRepository.findByEmail(userVlad.getEmail());

        assertThat(actualUser.getEmail(), is(userVlad.getEmail()));
        assertThat(actualUser.getWeight(), is(userVlad.getWeight()));
        assertThat(actualUser.getHeight(), is(userVlad.getHeight()));
    }

    @Test
    public void whenFindByGender_thenReturnUsers() {
        List<User> users = userRepository.findUsersByGender(MALE);

        assertThat(users, hasItems(userVlad));
        assertThat(users.get(0).getGender().toString(), is(MALE.toString()));
    }

    @Test
    public void whenFindUsersByRoles_thenReturnUsersWithThisRole() {
        List<User> users = userRepository.findUsersByRoles(roleUser);

        assertThat(users, hasSize(2));
        assertThat(users, hasItems(userDina, userVlad));
    }

    @Test
    public void whenFindUsersByAchievements_thenReturnUsersWithTisAchievement() {
        List<User> users = userRepository.findUsersByAchievements(achievementGood);

        assertThat(users, hasSize(1));
        assertThat(users, hasItem(userDina));
    }

    @Test
    public void whenCreateUser_thenCreatedUser() {
        User user = new User("userrito", "1231231231", "user@user.com");

        userRepository.saveAndFlush(user);

        assertThat(userRepository.count(), is(3L));
    }

    @Test
    public void whenCreateUserWithId_thenCreatedUserWithId() {
        User user = new User("userrito", "1231231231", "user@user.com");
        user.setId(3L);

        assertThat(userRepository.count(), is(3L));
    }

    @Test
    public void whenCreateUserWithRoles_thenCreatedUserWithRoles() {
        User user = new User("userrito", "1231231231", "user@user.com");
        user.setRoles(asList(roleUser, roleAdmin));

        User actualUser = userRepository.saveAndFlush(user);

        assertThat(userRepository.count(), is(3L));
        assertThat(actualUser.getRoles(), hasItems(roleAdmin, roleUser));
    }

    @Test
    public void whenCreateUserWithAchievements_thenCreatedUserWithAchievements() {
        User user = new User("userrito", "1231231231", "user@user.com");
        user.setAchievements(asList(achievementGreat, achievementGood));

        User actualUser = userRepository.saveAndFlush(user);

        assertThat(userRepository.count(), is(3L));
        assertThat(actualUser.getAchievements(), hasItems(achievementGood, achievementGreat));
    }

    @Test
    public void whenCreateUserWithRecords_thenCreatedUserWithRecords() {
        User user = new User("userrito", "1231231231", "user@user.com");
        user.setRecords(asList(record1, record2, record3));

        User actualUser = userRepository.saveAndFlush(user);

        assertThat(userRepository.count(), is(3L));
        assertThat(actualUser.getRecords(), hasSize(3));
    }

    @Test
    public void whenUpdateUserUsername_thenUpdatedUserUsername() {
        userDina.setUsername("No Dina");

        User actualUser = userRepository.saveAndFlush(userDina);

        assertThat(userRepository.count(), is(2L));
        assertThat(actualUser.getUsername(), is(userDina.getUsername()));
    }

    @Test
    public void whenUpdateUserPassword_thenUpdatedUserPassword() {
        userDina.setPassword("No Dina");

        User actualUser = userRepository.saveAndFlush(userDina);

        assertThat(userRepository.count(), is(2L));
        assertThat(actualUser.getPassword(), is(userDina.getPassword()));
    }

    @Test
    public void whenUpdateUserEmail_thenUpdatedUserEmail() {
        userDina.setEmail("jacoco@email.com");

        User actualUser = userRepository.saveAndFlush(userDina);

        assertThat(userRepository.count(), is(2L));
        assertThat(actualUser.getEmail(), is(userDina.getEmail()));
    }

    @Test
    public void whenUpdateUserRoles_thenUpdatedUserRole() {
        userDina.setRoles(new ArrayList<>(asList(roleAdmin, roleUser)));

        User actualUser = userRepository.saveAndFlush(userDina);

        assertThat(userRepository.count(), is(2L));
        assertThat(actualUser.getRoles(), hasItems(roleAdmin, roleUser));
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }
}
