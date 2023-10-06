package com.aston.rest.service;

import com.aston.rest.dto.RoleDto;
import com.aston.rest.entity.Role;
import com.aston.rest.repository.RoleRepository;
import com.aston.rest.util.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements RestService<Role, RoleDto> {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void create(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles
                .stream()
                .map(RoleMapper.instance::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto findById(Long id) {
        Role role = roleRepository
                .findById(id)
                .orElseGet(() -> new Role(-1L, "emptyRole", new ArrayList<>()));
        return RoleMapper.instance.entityToDto(role);
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
