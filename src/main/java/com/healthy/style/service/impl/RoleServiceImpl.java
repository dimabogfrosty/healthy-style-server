package com.healthy.style.service.impl;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.repository.RoleRepository;
import com.healthy.style.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getByName(final String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> getRolesByUser(final User user) {
        return roleRepository.findRolesByUsers(user);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(final Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role save(final Role entity) {
        return roleRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(final Long id) {
        roleRepository.deleteById(id);
    }

}
