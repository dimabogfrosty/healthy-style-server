package com.healthy.style.controller;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.service.RoleService;
import com.healthy.style.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAll();
    }

    @GetMapping("/roles/{id:\\d+}")
    public Role getRoleById(@PathVariable final Long id) {
        return roleService.getById(id);
    }

    @GetMapping("/roles/{id:\\d+}/users")
    public List<User> getUsersByRole(@PathVariable final Long id) {
        return userService.getUsersByRole(roleService.getById(id));
    }

    @PostMapping("/roles")
    public Role createRole(@RequestBody final Role role) {
        return roleService.save(role);
    }

    @PutMapping("/roles")
    public Role updateRole(@RequestBody final Role role) {
        return roleService.save(role);
    }

    @DeleteMapping("/roles/{id:\\d+}")
    public void deleteRole(@PathVariable final Long id) {
        roleService.delete(id);
    }

}
