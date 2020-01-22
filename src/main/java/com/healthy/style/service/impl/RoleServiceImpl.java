package com.healthy.style.service.impl;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.repository.RoleRepository;
import com.healthy.style.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> getByName(final String name) {
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
    public Optional<Role> getById(final Long id) {
        return roleRepository.findById(id);
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
