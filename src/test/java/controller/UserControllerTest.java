package controller;

import com.aston.rest.dto.UserDto;
import com.aston.rest.entity.Role;
import com.aston.rest.entity.User;
import com.aston.rest.service.UserService;
import com.aston.rest.controller.UserController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void testGetUserById() {
        Long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setUsername("TestUser");
        when(userService.findById(id)).thenReturn(userDto);

        UserDto result = userController.getById(id.toString());

        assertEquals(userDto, result);
    }

    @Test
    void testGetAllUsers() {
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setUsername("TestUser");
        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setUsername("TestUser");
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto1);
        userDtos.add(userDto2);
        when(userService.getAll()).thenReturn(userDtos);

        List<UserDto> result = userController.getAll();

        assertEquals(userDtos, result);
    }

    @Test
    void testAddUser() {
        User user = new User(1L, "TestUser", new Role());
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(userService).create(user);

        ResponseEntity<HttpStatus> result = userController.add(user);

        assertEquals(expectedResponse, result);
        verify(userService).create(user);
    }

    @Test
    void testUpdateUser() {
        Long id = 1L;
        User user = new User(id, "TestUser", new Role());
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(userService).update(user);

        ResponseEntity<HttpStatus> result = userController.update(user, id.toString());

        assertEquals(expectedResponse, result);
        verify(userService).update(user);
    }

    @Test
    void testDeleteUser() {
        Long id = 1L;
        User user = new User(id, "TestUser", new Role());
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(userService).delete(user);

        ResponseEntity<HttpStatus> result = userController.delete(user, id.toString());

        assertEquals(expectedResponse, result);
        verify(userService).delete(user);
    }
}