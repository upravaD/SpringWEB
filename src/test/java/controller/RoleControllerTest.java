package controller;

import com.aston.rest.dto.RoleDto;
import com.aston.rest.entity.Role;
import com.aston.rest.service.RoleService;
import com.aston.rest.controller.RoleController;

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
class RoleControllerTest {
    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @Test
    void testGetRoleById() {
        Long id = 1L;
        RoleDto roleDto = new RoleDto();
        roleDto.setId(id);
        roleDto.setName("TestRole");
        when(roleService.findById(id)).thenReturn(roleDto);

        RoleDto result = roleController.getById(id.toString());

        assertEquals(roleDto, result);
    }

    @Test
    void testGetAllRoles() {
        RoleDto roleDto1 = new RoleDto();
        roleDto1.setId(1L);
        roleDto1.setName("TestRole");
        RoleDto roleDto2 = new RoleDto();
        roleDto2.setId(2L);
        roleDto2.setName("TestRole");
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDtos.add(roleDto1);
        roleDtos.add(roleDto2);
        when(roleService.getAll()).thenReturn(roleDtos);

        List<RoleDto> result = roleController.getAll();

        assertEquals(roleDtos, result);
    }

    @Test
    void testAddRole() {
        Role role = new Role(1L, "TestRole", new ArrayList<>());
        ResponseEntity<HttpStatus> response = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(roleService).create(role);

        ResponseEntity<HttpStatus> result = roleController.add(role);

        assertEquals(response, result);
        verify(roleService).create(role);
    }

    @Test
    void testUpdateRole() {
        Long id = 1L;
        Role role = new Role(id, "TestRole", new ArrayList<>());
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(roleService).update(role);

        ResponseEntity<HttpStatus> result = roleController.update(role, id.toString());

        assertEquals(expectedResponse, result);
        verify(roleService).update(role);
    }

    @Test
    void testDeleteRole() {
        Long id = 1L;
        Role role = new Role(id, "TestRole", new ArrayList<>());
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(roleService).delete(role);

        ResponseEntity<HttpStatus> result = roleController.delete(role, id.toString());

        assertEquals(expectedResponse, result);
        verify(roleService).delete(role);
    }
}