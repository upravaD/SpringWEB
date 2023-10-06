package repository;

import config.TestConfig;
import com.aston.rest.entity.Permission;
import com.aston.rest.repository.PermissionRepository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.transaction.annotation.Transactional;
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
class PermissionRepositoryTest {

    PermissionRepository permissionRepository;

    @Autowired
    public PermissionRepositoryTest(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
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
    void testCreatePermission() {
        Permission permission = new Permission(1L, "createTestPermissionName");

        Permission testPermission = permissionRepository.save(permission);

        assertInstanceOf(Permission.class, testPermission);
        assertNotNull(testPermission.getId());
        assertEquals(testPermission.getPermissionName(), permission.getPermissionName());
    }

    @Test
    @Order(value = 2)
    void testFindPermissionById() {
        Permission permission = new Permission();
        permission.setPermissionName("findByIDTestPermissionName");
        Permission testPermission = permissionRepository.save(permission);

        Optional<Permission> optionalPermission = permissionRepository.findById(1L);
        if (optionalPermission.isPresent()) permission = optionalPermission.get();

        assertNotNull(optionalPermission);
        assertInstanceOf(Optional.class, optionalPermission);
        assertEquals(testPermission.getId(), permission.getId());
        assertEquals(testPermission.getPermissionName(), permission.getPermissionName());
    }

    @Test
    @Order(value = 3)
    void testGetAllPermissions() {
        for (int i = 1; i <= 10; i++) {
            Permission permission = new Permission();
            permission.setPermissionName("TestPermissionName" + i);
            permissionRepository.save(permission);
        }

        List<Permission> permissionList = permissionRepository.findAll();

        assertNotNull(permissionList);
        assertEquals(11, permissionList.size());
        assertEquals("TestPermissionName10", permissionList.get(10).getPermissionName());
    }

    @Test
    @Order(value = 4)
    @Transactional
    void testUpdatePermission() {
        Permission permission = new Permission();
        Optional<Permission> optionalPermission = permissionRepository.findById(2L);
        if (optionalPermission.isPresent()) permission = optionalPermission.get();
        permission.setPermissionName("TestPermissionName0");

        Permission testPermission = permissionRepository.save(permission);

        assertEquals(testPermission.getId(), permission.getId());
        assertEquals(testPermission.getPermissionName(), permission.getPermissionName());
        assertEquals(11, permissionRepository.findAll().size());
    }

    @Test
    @Order(value = 5)
    @Transactional
    void testDeleteCourse() {
        permissionRepository.deleteById(2L);
        assertEquals(Optional.empty(), permissionRepository.findById(2L));
        assertEquals(10, permissionRepository.findAll().size());

        permissionRepository.deleteAll();
        assertEquals(List.of(), permissionRepository.findAll());
    }
}