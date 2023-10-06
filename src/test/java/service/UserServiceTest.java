package service;

import com.aston.rest.dto.UserDto;
import com.aston.rest.entity.Role;
import com.aston.rest.entity.User;
import com.aston.rest.repository.UserRepository;
import com.aston.rest.service.UserService;
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
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        User user = new User(1L, "testUser", new Role());
        when(userRepository.save(user)).thenReturn(user);

        userService.create(user);

        verify(userRepository).save(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "user1", new Role()));
        userList.add(new User(2L, "user2", new Role()));

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> result = userService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindUserById() {
        User user = new User(1L, "testUser", new Role());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto result = userService.findById(1L);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void testFindUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserDto result = userService.findById(1L);

        assertEquals(-1L, result.getId());
        assertEquals("emptyUser", result.getUsername());
    }

    @Test
    void testUpdateUser() {
        User user = new User(1L, "testUser", new Role());
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        userService.update(user);

        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User(1L, "testUser", new Role());
        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.delete(user);

        verify(userRepository).delete(user);
    }
}
