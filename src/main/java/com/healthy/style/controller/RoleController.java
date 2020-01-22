package com.healthy.style.controller;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.service.RoleService;
import com.healthy.style.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    public ResponseEntity<?> getRoleById(@PathVariable final Long id) {
        Optional<Role> role = roleService.getById(id);
        return role.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/roles/{id:\\d+}/users")
    public List<User> getUsersByRole(@PathVariable final Long id) {
        Role role = roleService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity: " + Role.class.getName() +
                        " with id: " + id + " not found"));
        return userService.getUsersByRole(role);
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@Valid @RequestBody final Role role) throws URISyntaxException {
        Role createdRole = roleService.save(role);
        return ResponseEntity.created(new URI("api/roles/" + createdRole.getId())).body(createdRole);
    }

    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody final Role role) {
        Role updatedRole = roleService.save(role);
        return ResponseEntity.ok().body(updatedRole);
    }

    @DeleteMapping("/roles/{id:\\d+}")
    public ResponseEntity<?> deleteRole(@PathVariable final Long id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }

}
