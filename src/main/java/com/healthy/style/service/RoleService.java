package com.healthy.style.service;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;

import java.util.List;

public interface RoleService extends EntityService<Role> {

    Role getByName(String name);

    List<Role> getRolesByUser(User user);

}
