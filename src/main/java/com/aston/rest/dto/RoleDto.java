package com.aston.rest.dto;

import com.aston.rest.entity.Permission;

import java.util.ArrayList;
import java.util.List;

public class RoleDto {
    private Long id;
    private String name;
    private List<Permission> permissions = new ArrayList<>();

    public RoleDto() {
    }

    public RoleDto(Long id, String name, List<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
