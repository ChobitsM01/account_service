package io.reflectoring.accoutService.service.role;

import io.reflectoring.accoutService.dto.request.RoleRequestDTO;
import io.reflectoring.accoutService.entity.Role;
import io.reflectoring.accoutService.exception.AppException;

import java.util.List;

public interface RoleService{
    List<Role> getAllRoles();
    String deleteRole(String name) throws AppException;
    Role createRole(RoleRequestDTO requestDTO);
}
