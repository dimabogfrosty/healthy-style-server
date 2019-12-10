package com.healthy.style.repository;

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
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    private User userDina;
    private User userVlad;
    private Role roleAdmin;
    private Role roleUser;

    @Before
    public void setUp() {
        userDina = new User("dinaa", "dddddd", "dina@email.com");
        userVlad = new User("vladis", "vvvvvvvv", "vlad@email.com");

        roleAdmin = new Role("ADMIN");
        roleAdmin.setUsers(new ArrayList<>(singletonList(userDina)));
        entityManager.persistAndFlush(roleAdmin);

        roleUser = new Role("USER");
        roleUser.setUsers(new ArrayList<>(asList(userDina, userVlad)));
        entityManager.persistAndFlush(roleUser);

        userDina.setRoles(new ArrayList<>(asList(roleAdmin, roleUser)));
        entityManager.persistAndFlush(userDina);

        userVlad.setRoles(new ArrayList<>(singletonList(roleUser)));
        entityManager.persistAndFlush(userVlad);
    }

    @Test
    public void whenFindByName_thenReturnRole() {
        Role actualRole = roleRepository.findByName(roleAdmin.getName());

        assertThat(actualRole.getName(), is(roleAdmin.getName()));
    }

    @Test
    public void whenFindByName_thenReturnRoleWithUsers() {
        Role actualRole = roleRepository.findByName(roleUser.getName());

        assertThat(actualRole.getName(), is(roleUser.getName()));
        assertThat(actualRole.getUsers(), hasItems(userDina, userVlad));
    }

    @Test
    public void whenFindRolesByUsers_thenReturnRolesThisUser() {
        List<Role> roles = roleRepository.findRolesByUsers(userDina);

        assertThat(roles.stream().map(Role::getName).collect(toList()), hasItems("ADMIN", "USER"));
    }

    @Test
    public void whenCreateRole_thenReturnCreatedRole() {
        Role expectedRole = new Role("DEVELOPER");

        Role actualRole = roleRepository.saveAndFlush(expectedRole);

        assertThat(actualRole.getName(), is(expectedRole.getName()));
    }

    @Test
    public void whenCreateRole_thenReturnCreatedRoleWithUser() {
        Role expectedRole = new Role("DEVELOPER");
        expectedRole.setUsers(singletonList(userDina));

        Role actualRole = roleRepository.saveAndFlush(expectedRole);

        assertThat(actualRole.getName(), is(expectedRole.getName()));
        assertThat(actualRole.getUsers(), is(expectedRole.getUsers()));
    }

    @Test
    public void whenUpdateRole_thenReturnUpdatedRole() {
        roleAdmin.setName("DEVELOPER");

        Role actualRole = roleRepository.saveAndFlush(roleAdmin);

        assertThat(actualRole.getName(), is(roleAdmin.getName()));
    }

    @Test
    public void whenUpdateRole_thenReturnUpdatedRoleWithUser() {
        Role RoleDeveloper = new Role("DEVELOPER");
        RoleDeveloper.setUsers(new ArrayList<>(singletonList(userDina)));
        entityManager.persistAndFlush(RoleDeveloper);
        RoleDeveloper.getUsers().add(userVlad);

        Role actualRole = roleRepository.saveAndFlush(RoleDeveloper);

        assertThat(actualRole.getUsers(), is(RoleDeveloper.getUsers()));
    }

    @Test
    public void whenDeleteRole_thenDeletingShouldBeSuccessful() {
        roleRepository.delete(roleAdmin);

        assertThat(roleRepository.count(), is(1L));
    }

    @Test(expected = PersistenceException.class)
    public void whenCreateRoleWithDuplicateName_thenThrowException() {
        entityManager.persistAndFlush(new Role("USER"));
        entityManager.persistAndFlush(new Role("USER"));
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenCreateRoleWithNameLengthMore20_thenThrowException() {
        entityManager.persistAndFlush(new Role("USERUSERUSERUSERUSERUSER"));
    }

    @Test(expected = PersistenceException.class)
    public void whenCreateRoleWithNullName_thenThrowException() {
        entityManager.persistAndFlush(new Role());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

}
