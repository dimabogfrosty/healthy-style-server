package com.healthy.style.service;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.repository.RoleRepository;
import com.healthy.style.service.impl.RoleServiceImpl;
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
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private User userDina;
    private Role roleAdmin;
    private Role roleUser;

    @Before
    public void setUp() {
        userDina = new User("dinaa", "dddddd", "dina@email.com");
        User userVlad = new User("vladis", "vvvvvvvv", "vlad@email.com");

        roleAdmin = new Role("ADMIN");
        roleAdmin.setId(1L);
        roleAdmin.setUsers(new ArrayList<>(singletonList(userDina)));

        roleUser = new Role("USER");
        roleUser.setId(2L);
        roleUser.setUsers(new ArrayList<>(asList(userDina, userVlad)));

        userDina.setRoles(new ArrayList<>(asList(roleAdmin, roleUser)));

        userVlad.setRoles(new ArrayList<>(singletonList(roleUser)));
    }

    @Test
    public void whenGetAll_thenReturnRoles() {
        when(roleRepository.findAll()).thenReturn(asList(roleAdmin, roleUser));

        List<Role> roles = roleService.getAll();

        assertThat(roles, hasSize(2));
        assertThat(roles, hasItems(roleAdmin, roleUser));
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void whenGetById_thenReturnRole() {
        when(roleRepository.getOne(roleAdmin.getId())).thenReturn(roleAdmin);

        Role actualRole = roleService.getById(roleAdmin.getId());

        assertThat(actualRole.getId(), is(roleAdmin.getId()));
        verify(roleRepository, timeout(1)).getOne(roleAdmin.getId());
    }

    @Test
    public void whenGetByName_thenReturnRole() {
        when(roleRepository.findByName(roleAdmin.getName())).thenReturn(roleAdmin);

        Role actualRole = roleService.getByName(roleAdmin.getName());

        assertThat(actualRole.getName(), is(roleAdmin.getName()));
        verify(roleRepository, timeout(1)).findByName(roleAdmin.getName());
    }

    @Test
    public void whenGetRolesByUser_thenReturnRoles() {
        when(roleRepository.findRolesByUsers(userDina)).thenReturn(asList(roleUser, roleAdmin));

        List<Role> roles = roleService.getRolesByUser(userDina);

        assertThat(roles, hasSize(2));
        assertThat(roles, hasItems(roleAdmin, roleUser));
        verify(roleRepository, timeout(1)).findRolesByUsers(userDina);
    }

    @Test
    public void whenSaveRole_thenReturnRole() {
        when(roleRepository.saveAndFlush(any(Role.class))).thenReturn(new Role());

        Role actualRole = roleService.save(roleAdmin);

        assertThat(actualRole, instanceOf(Role.class));
        verify(roleRepository, times(1)).saveAndFlush(roleAdmin);
    }

    @Test
    public void whenDeleteRole_thenDeletingShouldBeSuccessful() {
        roleService.delete(roleAdmin.getId());

        verify(roleRepository, times(1)).deleteById(roleAdmin.getId());
    }

}
