package io.reflectoring.accoutService.service.permission;

import io.reflectoring.accoutService.dto.request.PermissionRequestDTO;
import io.reflectoring.accoutService.dto.response.PermissionResponseDTO;
import io.reflectoring.accoutService.entity.Permission;
import io.reflectoring.accoutService.exception.AppException;

import java.util.List;

public interface PermissionService {
    PermissionResponseDTO createPermission(PermissionRequestDTO requestDTO) throws AppException;
    String deletePermission(String name) throws AppException;
    List<Permission> getPermissions();
}
