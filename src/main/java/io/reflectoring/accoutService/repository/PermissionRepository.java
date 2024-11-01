package io.reflectoring.accoutService.repository;

import io.reflectoring.accoutService.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
