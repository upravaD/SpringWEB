package com.aston.rest.util.mapper;

import com.aston.rest.dto.UserDto;
import com.aston.rest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper instance = Mappers.getMapper(UserMapper.class);
    UserDto entityToDto(User user);
}
