package io.reflectoring.accoutService.service.role;

import io.reflectoring.accoutService.dto.request.RoleRequestDTO;
import io.reflectoring.accoutService.entity.Permission;
import io.reflectoring.accoutService.entity.Role;
import io.reflectoring.accoutService.exception.AppException;
import io.reflectoring.accoutService.exception.ErrorCode;
import io.reflectoring.accoutService.repository.PermissionRepository;
import io.reflectoring.accoutService.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Role createRole(RoleRequestDTO requestDTO) {
        Role role = new Role();
        role.setName(requestDTO.getName());
        role.setDescription(requestDTO.getDescription());

        Set<Permission> permissions = new HashSet<>();
        for (String permissionName : requestDTO.getPermissions()) {
            permissionRepository.findById(permissionName).ifPresent(permissions::add);
        }
        role.setPermissions(permissions);

        return roleRepository.save(role);
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public String deleteRole(String name) throws AppException {
        Role existedRole = roleRepository.findById(name)
                .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        roleRepository.delete(existedRole);
        return "Role has been deleted with id: " + name;
    }
}
