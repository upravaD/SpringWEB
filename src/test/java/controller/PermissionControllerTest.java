package controller;

import com.aston.rest.dto.PermissionDto;
import com.aston.rest.entity.Permission;
import com.aston.rest.service.PermissionService;
import com.aston.rest.controller.PermissionController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
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
class PermissionControllerTest {
    @InjectMocks
    private PermissionController permissionController;

    @Mock
    private PermissionService permissionService;

    @Test
    void testGetPermissionById() {
        Long id = 1L;
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(id);
        permissionDto.setPermissionName("TestPermission");
        when(permissionService.findById(id)).thenReturn(permissionDto);

        PermissionDto result = permissionController.getById(id.toString());

        assertEquals(permissionDto, result);
    }

    @Test
    void testGetAllPermissions() {
        PermissionDto permissionDto1 = new PermissionDto();
        permissionDto1.setId(1L);
        permissionDto1.setPermissionName("TestPermission1");
        PermissionDto permissionDto2 = new PermissionDto();
        permissionDto2.setId(2L);
        permissionDto2.setPermissionName("TestPermission2");
        List<PermissionDto> permissionDtos = new ArrayList<>();
        permissionDtos.add(permissionDto1);
        permissionDtos.add(permissionDto2);
        when(permissionService.getAll()).thenReturn(permissionDtos);

        List<PermissionDto> result = permissionController.getAll();

        assertEquals(permissionDtos, result);
    }

    @Test
    void testAddPermission() {
        Permission permission = new Permission(1L, "TestPermission");
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(permissionService).create(permission);

        ResponseEntity<HttpStatus> result = permissionController.add(permission);

        assertEquals(expectedResponse, result);
        Mockito.verify(permissionService).create(permission);
    }

    @Test
    void testUpdatePermission() {
        Long id = 1L;
        Permission permission = new Permission(id, "TestPermission");
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(permissionService).update(permission);

        ResponseEntity<HttpStatus> result = permissionController.update(permission, id.toString());

        assertEquals(expectedResponse, result);
        verify(permissionService).update(permission);
    }

    @Test
    void testDeletePermission() {
        Long id = 1L;
        Permission permission = new Permission(id, "TestPermission");
        ResponseEntity<HttpStatus> expectedResponse = ResponseEntity.ok(HttpStatus.OK);
        doNothing().when(permissionService).delete(permission);

        ResponseEntity<HttpStatus> result = permissionController.delete(permission, id.toString());

        assertEquals(expectedResponse, result);
        verify(permissionService).delete(permission);
    }
}