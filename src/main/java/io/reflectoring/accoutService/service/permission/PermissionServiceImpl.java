package io.reflectoring.accoutService.service.permission;

import io.reflectoring.accoutService.dto.request.PermissionRequestDTO;
import io.reflectoring.accoutService.dto.response.PermissionResponseDTO;
import io.reflectoring.accoutService.entity.Permission;
import io.reflectoring.accoutService.exception.AppException;
import io.reflectoring.accoutService.exception.ErrorCode;
import io.reflectoring.accoutService.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public PermissionResponseDTO createPermission(PermissionRequestDTO requestDTO) {
        Permission permission = new Permission();
        permission.setName(requestDTO.getName());
        permission.setDescription(requestDTO.getDescription());
        permissionRepository.save(permission);
        PermissionResponseDTO responseDTO = new PermissionResponseDTO();
        responseDTO.setName(permission.getName());
        responseDTO.setDescription(permission.getDescription());
        return responseDTO;
    }

    public List<Permission> getPermissions() {
        return permissionRepository.findAll();
    }

    public String deletePermission(String name) throws AppException {
        Permission existedPermission = permissionRepository.findById(name)
                .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permissionRepository.delete(existedPermission);
        return "Permission deleted with id: " + name;
    }
}
