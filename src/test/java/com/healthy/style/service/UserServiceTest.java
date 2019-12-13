package com.healthy.style.service;

import com.healthy.style.entity.Achievement;
import com.healthy.style.entity.Record;
import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.repository.UserRepository;
import com.healthy.style.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.healthy.style.entity.User.Gender.FEMALE;
import static java.time.Month.DECEMBER;
import static java.time.Month.MAY;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User userDina;
    private Role roleAdmin;
    private Achievement achievementGood;

    @Before
    public void setUp() {
        userDina = new User("dinaa", "dddddd", "dina@email.com");
        userDina.setId(1L);
        userDina.setFirstName("Dina");
        userDina.setLastName("Evgeni");
        userDina.setBirthDate(LocalDate.of(2000, MAY, 20));
        userDina.setGender(FEMALE);
        userDina.setWeight(58.6);
        userDina.setHeight(160);

        Record record1 = new Record(
                LocalDate.of(2019, DECEMBER, 1),
                LocalTime.of(2, 24, 0),
                LocalTime.of(3, 58, 0), 2.3D);
        record1.setUser(userDina);

        roleAdmin = new Role("ADMIN");
        roleAdmin.setUsers(new ArrayList<>(singletonList(userDina)));

        achievementGood = new Achievement("Good", "image.png");
        achievementGood.setUsers(new ArrayList<>(singletonList(userDina)));
    }

    @Test
    public void whenGetByUsername_thenReturnUser() {
        when(userRepository.findByUsername(userDina.getUsername())).thenReturn(userDina);

        User actualUser = userService.getByUsername(userDina.getUsername());

        assertThat(actualUser.getUsername(), is(userDina.getUsername()));
        verify(userRepository, times(1)).findByUsername(userDina.getUsername());
    }

    @Test
    public void whenGetByEmail_thenReturnUser() {
        when(userRepository.findByEmail(userDina.getEmail())).thenReturn(userDina);

        User actualUser = userService.getByEmail(userDina.getEmail());

        assertThat(actualUser.getEmail(), is(userDina.getEmail()));
        verify(userRepository, times(1)).findByEmail(userDina.getEmail());
    }

    @Test
    public void whenGetById_thenReturnUser() {
        when(userRepository.getOne(userDina.getId())).thenReturn(userDina);

        User actualUser = userService.getById(userDina.getId());

        assertThat(actualUser.getId(), is(userDina.getId()));
        verify(userRepository, times(1)).getOne(userDina.getId());
    }

    @Test
    public void whenGetAll_thenReturnUsers() {
        when(userRepository.findAll()).thenReturn(singletonList(userDina));

        List<User> users = userService.getAll();

        assertThat(users, hasSize(1));
        assertThat(users, hasItem(userDina));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void whenGetByGender_thenReturnUsers() {
        when(userRepository.findUsersByGender(FEMALE)).thenReturn(singletonList(userDina));

        List<User> users = userService.getByGender(FEMALE);

        assertThat(users, hasSize(1));
        assertThat(users, hasItem(userDina));
        verify(userRepository, times(1)).findUsersByGender(userDina.getGender());
    }

    @Test
    public void whenGetUsersByRole_thenReturnUsers() {
        when(userRepository.findUsersByRoles(roleAdmin)).thenReturn(singletonList(userDina));

        List<User> users = userService.getUsersByRole(roleAdmin);

        assertThat(users, hasSize(1));
        assertThat(users, hasItem(userDina));
        verify(userRepository, times(1)).findUsersByRoles(roleAdmin);
    }

    @Test
    public void whenGetByAchievement_thenReturnUsers() {
        when(userRepository.findUsersByAchievements(achievementGood)).thenReturn(singletonList(userDina));

        List<User> users = userService.getUsersByAchievement(achievementGood);

        assertThat(users, hasSize(1));
        assertThat(users, hasItem(userDina));
        verify(userRepository, times(1)).findUsersByAchievements(achievementGood);
    }

    @Test
    public void whenSaveUser_thenReturnUser() {
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(new User());

        User actualUser = userService.save(userDina);

        assertThat(actualUser, instanceOf(User.class));
        verify(userRepository, times(1)).saveAndFlush(userDina);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenDeleteUser_thenThrowException() {
        userService.delete(userDina.getId());
    }

}
