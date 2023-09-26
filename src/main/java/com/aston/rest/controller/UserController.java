package com.aston.rest.controller;

import com.aston.rest.dto.UserDto;
import com.aston.rest.entity.User;
import com.aston.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable String id) {
        return userService.findById(Long.parseLong(id));
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> add(@RequestBody User user) {
        userService.create(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody User user, @PathVariable String id) {
        user.setId(Long.parseLong(id));
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@RequestBody User user, @PathVariable String id) {
        user.setId(Long.parseLong(id));
        userService.delete(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}