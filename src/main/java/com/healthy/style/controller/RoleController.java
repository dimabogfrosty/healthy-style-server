package com.healthy.style.controller;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.service.RoleService;
import com.healthy.style.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    private RoleService roleService;
    private UserService userService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAll();
    }

    @GetMapping("/roles/{id:\\d+}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @GetMapping("/roles/{id:\\d+}/users")
    public List<User> getUsersByRole(@PathVariable Long id) {
        return userService.getUsersByRole(roleService.getById(id));
    }

    @PostMapping("/roles")
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/roles")
    public Role updateRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @DeleteMapping("/roles/{id:\\d+}")
    public void deleteRole(@PathVariable Long id) {
        roleService.delete(id);
    }

}
