package io.reflectoring.accoutService.repository;

import io.reflectoring.accoutService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
