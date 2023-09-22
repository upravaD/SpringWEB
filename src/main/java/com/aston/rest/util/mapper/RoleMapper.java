package com.aston.rest.util.mapper;

import com.aston.rest.dto.RoleDto;
import com.aston.rest.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper instance = Mappers.getMapper(RoleMapper.class);
    RoleDto entityToDto(Role role);
}
