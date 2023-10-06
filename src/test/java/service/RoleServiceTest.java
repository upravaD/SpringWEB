package service;

import com.aston.rest.entity.Role;
import com.aston.rest.dto.RoleDto;
import com.aston.rest.service.RoleService;
import com.aston.rest.repository.RoleRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class RoleServiceTest {
    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void testCreateRole() {
        Role role = new Role(1L, "testRole", new ArrayList<>());
        when(roleRepository.save(role)).thenReturn(role);

        roleService.create(role);

        verify(roleRepository).save(role);
    }

    @Test
    void testGetAllRoles() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role(1L, "testRole1", new ArrayList<>()));
        roleList.add(new Role(2L, "testRole2", new ArrayList<>()));

        when(roleRepository.findAll()).thenReturn(roleList);

        List<RoleDto> result = roleService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindRoleById() {
        Role role = new Role(1L, "testRole", new ArrayList<>());
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        RoleDto result = roleService.findById(1L);

        assertEquals(role.getId(), result.getId());
        assertEquals(role.getName(), result.getName());
    }

    @Test
    void testFindRoleByIdNotFound() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        RoleDto result = roleService.findById(1L);

        assertEquals(-1L, result.getId());
        assertEquals("emptyRole", result.getName());
    }

    @Test
    void testUpdateRole() {
        Role role = new Role(1L, "testRole", new ArrayList<>());
        when(roleRepository.save(role)).thenReturn(role);

        roleService.update(role);

        verify(roleRepository).save(role);
    }

    @Test
    void testDeleteRole() {
        Role role = new Role(1L, "testRole", new ArrayList<>());

        roleService.delete(role);

        verify(roleRepository).delete(role);
    }
}
