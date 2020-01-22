package com.healthy.style.service;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;

import java.util.List;
import java.util.Optional;

public interface RoleService extends EntityService<Role> {

    Optional<Role> getByName(String name);

    List<Role> getRolesByUser(User user);

}
