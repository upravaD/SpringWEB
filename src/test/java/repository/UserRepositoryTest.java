package repository;

import config.TestConfig;
import com.aston.rest.entity.Role;
import com.aston.rest.entity.User;
import com.aston.rest.repository.RoleRepository;
import com.aston.rest.repository.UserRepository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {
    RoleRepository roleRepository;
    UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Container
    private final static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withInitScript("test.sql")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("hibernate.connection.url", postgreSQLContainer::getJdbcUrl);
        registry.add("hibernate.connection.username", postgreSQLContainer::getUsername);
        registry.add("hibernate.connection.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void setUp() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    @Order(value = 1)
    @Transactional
    void testCreateUser() {
        User user = new User(1L, "createTestUserName", new Role());

        User testUser = userRepository.save(user);

        assertInstanceOf(User.class, testUser);
        assertNotNull(testUser.getId());
        assertEquals(testUser.getUsername(), testUser.getUsername());
        assertEquals(testUser.getRole().getId(), user.getRole().getId());
    }

    @Test
    @Order(value = 2)
    void testFindUserById() {
        User user = new User();
        user.setUsername("findByIDTestRoleName");
        User testUser = userRepository.save(user);

        Optional<User> optionalUser = userRepository.findById(1L);
        if (optionalUser.isPresent()) user = optionalUser.get();

        assertNotNull(optionalUser);
        assertInstanceOf(Optional.class, optionalUser);
        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getUsername(), user.getUsername());
    }

    @Test
    @Order(value = 3)
    void testGetAllUser() {
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setUsername("TestUserName" + i);
            userRepository.save(user);
        }

        List<User> userList = userRepository.findAll();

        assertNotNull(userList);
        assertEquals(11, userList.size());
        assertEquals("TestUserName10", userList.get(10).getUsername());
    }

    @Test
    @Order(value = 4)
    @Transactional
    void testUpdateUser() {
        Role role = new Role();
        role.setName("testRoleName");
        roleRepository.save(role);

        User user = new User();
        Optional<User> optionalUser = userRepository.findById(2L);
        if (optionalUser.isPresent()) user = optionalUser.get();
        user.setUsername("updatedUserName");
        user.setRole(role);

        User testUser = userRepository.save(user);

        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getUsername(), user.getUsername());
        assertEquals(testUser.getRole().getId(), user.getRole().getId());
        assertEquals(testUser.getRole().getName(), user.getRole().getName());
        assertEquals(11, userRepository.findAll().size());
    }

    @Test
    @Order(value = 5)
    @Transactional
    void testDeleteUser() {
        userRepository.deleteById(2L);
        assertEquals(Optional.empty(), userRepository.findById(2L));
        assertEquals(10, userRepository.findAll().size());

        userRepository.deleteAll();
        assertEquals(List.of(), userRepository.findAll());
    }
}