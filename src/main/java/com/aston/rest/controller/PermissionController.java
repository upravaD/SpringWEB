package com.aston.rest.controller;

import com.aston.rest.dto.PermissionDto;
import com.aston.rest.entity.Permission;
import com.aston.rest.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{id}")
    public PermissionDto getById(@PathVariable String id) {
        return permissionService.findById(Long.parseLong(id));
    }

    @GetMapping
    public List<PermissionDto> getAll() {
        return permissionService.getAll();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody Permission permission) {
        permissionService.create(permission);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody Permission permission, @PathVariable String id) {
        permission.setId(Long.parseLong(id));
        permissionService.update(permission);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@RequestBody Permission permission, @PathVariable String id) {
        permission.setId(Long.parseLong(id));
        permissionService.delete(permission);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}