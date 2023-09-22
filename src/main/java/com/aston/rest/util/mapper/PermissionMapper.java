package com.aston.rest.util.mapper;

import com.aston.rest.dto.PermissionDto;
import com.aston.rest.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionMapper {
    PermissionMapper instance = Mappers.getMapper(PermissionMapper.class);
    PermissionDto entityToDto(Permission permission);
}
