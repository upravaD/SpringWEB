package com.aston.rest.controller;

import com.aston.rest.dto.RoleDto;
import com.aston.rest.entity.Role;
import com.aston.rest.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable String id) {
        return roleService.findById(Long.parseLong(id));
    }

    @GetMapping
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody Role role) {
        roleService.create(role);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody Role role,
                                             @PathVariable String id) {
        role.setId(Long.parseLong(id));
        roleService.update(role);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@RequestBody Role role,
                                             @PathVariable String id) {
        role.setId(Long.parseLong(id));
        roleService.delete(role);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}