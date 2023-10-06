package repository;

import config.TestConfig;
import com.aston.rest.entity.Role;
import com.aston.rest.entity.Permission;
import com.aston.rest.repository.RoleRepository;
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
class RoleRepositoryTest {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
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
    void testCreateRole() {
        Role role = new Role(1L, "createTestRoleName", List.of(new Permission()));

        Role testRole = roleRepository.save(role);

        assertInstanceOf(Role.class, testRole);
        assertNotNull(testRole.getId());
        assertEquals(testRole.getName(), testRole.getName());
        assertEquals(testRole.getPermissions().get(0).getId(),
                role.getPermissions().get(0).getId());
    }

    @Test
    @Order(value = 2)
    void testFindRoleById() {
        Role role = new Role();
        role.setName("findByIDTestRoleName");
        Role testRole = roleRepository.save(role);

        Optional<Role> optionalRole = roleRepository.findById(1L);
        if (optionalRole.isPresent()) role = optionalRole.get();

        assertNotNull(optionalRole);
        assertInstanceOf(Optional.class, optionalRole);
        assertEquals(testRole.getId(), role.getId());
        assertEquals(testRole.getName(), role.getName());
    }

    @Test
    @Order(value = 3)
    void testGetAllRole() {
        for (int i = 1; i <= 10; i++) {
            Role role = new Role();
            role.setName("TestRoleName" + i);
            roleRepository.save(role);
        }

        List<Role> roleList = roleRepository.findAll();

        assertNotNull(roleList);
        assertEquals(11, roleList.size());
        assertEquals("TestRoleName10", roleList.get(10).getName());
    }

    @Test
    @Order(value = 4)
    @Transactional
    void testUpdateRole() {
        Permission permission1 = new Permission();
        permission1.setPermissionName("testPermission1");
        Permission permission2 = new Permission();
        permission2.setPermissionName("testPermission2");
        permissionRepository.saveAll(List.of(permission1, permission2));
        List<Permission> permissions = permissionRepository.findAll();

        Role role = new Role();
        Optional<Role> optionalRole = roleRepository.findById(2L);
        if (optionalRole.isPresent()) role = optionalRole.get();
        role.setName("updatedRoleName");
        role.setPermissions(permissions);

        Role testRole = roleRepository.save(role);

        assertEquals(testRole.getId(), role.getId());
        assertEquals(testRole.getName(), role.getName());
        assertEquals(testRole.getPermissions().size(), role.getPermissions().size());
        assertEquals(testRole.getPermissions().get(0).getPermissionName(), role.getPermissions().get(0).getPermissionName());
        assertEquals(testRole.getPermissions().get(1).getPermissionName(), role.getPermissions().get(1).getPermissionName());
        assertEquals(11, roleRepository.findAll().size());
    }

    @Test
    @Order(value = 5)
    @Transactional
    void testDeleteRole() {
        roleRepository.deleteById(2L);
        assertEquals(Optional.empty(), roleRepository.findById(2L));
        assertEquals(10, roleRepository.findAll().size());

        roleRepository.deleteAll();
        assertEquals(List.of(), roleRepository.findAll());
    }
}