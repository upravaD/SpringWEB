package service;

import com.aston.rest.dto.PermissionDto;
import com.aston.rest.entity.Permission;
import com.aston.rest.repository.PermissionRepository;
import com.aston.rest.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class PermissionServiceTest {
    @Mock
    private PermissionRepository repository;
    @InjectMocks
    private PermissionService service;

    @Test
    void createPermission() {
        Permission permission = new Permission(1L, "testPermission");
        when(repository.save(permission)).thenReturn(permission);

        service.create(permission);

        verify(repository).save(permission);
    }

    @Test
    void testGetAllPermissions() {
        List<Permission> permissionList = new ArrayList<>();
        permissionList.add(new Permission(1L, "permission1"));
        permissionList.add(new Permission(2L, "permission2"));
        when(repository.findAll()).thenReturn(permissionList);

        List<PermissionDto> result = service.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindPermissionById() {
        Permission permission = new Permission(1L, "testPermission");
        when(repository.findById(1L)).thenReturn(Optional.of(permission));

        PermissionDto result = service.findById(1L);

        assertEquals(permission.getId(), result.getId());
        assertEquals(permission.getPermissionName(), result.getPermissionName());
    }

    @Test
    void testFindPermissionByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        PermissionDto result = service.findById(1L);

        assertEquals(-1L, result.getId());
        assertEquals("emptyPermission", result.getPermissionName());
    }

    @Test
    void testUpdatePermission() {
        Permission permission = new Permission(1L, "testPermission");
        when(repository.save(permission)).thenReturn(permission);

        service.update(permission);

        verify(repository).save(permission);
    }

    @Test
    void testDeletePermission() {
        Permission permission = new Permission(1L, "testPermission");

        service.delete(permission);

        verify(repository).delete(permission);
    }

}
