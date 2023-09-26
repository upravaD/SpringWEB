package com.aston.rest.repository;

import com.aston.rest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "insert into role_permissions (role_id,permission_id) values (?, ?)",
           nativeQuery = true)
    void updateRolePermissions(Long roleId, Long permissionId);

}
