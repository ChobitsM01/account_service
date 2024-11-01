package io.reflectoring.accoutService.controller;

import io.reflectoring.accoutService.dto.request.PermissionRequestDTO;
import io.reflectoring.accoutService.dto.response.PermissionResponseDTO;
import io.reflectoring.accoutService.dto.response.ResponseApi;
import io.reflectoring.accoutService.entity.Permission;
import io.reflectoring.accoutService.exception.AppException;
import io.reflectoring.accoutService.service.permission.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @DeleteMapping("/{name}")
    public ResponseApi<String> delete(@PathVariable String name) throws AppException {
        ResponseApi<String> result = new ResponseApi<>();
        result.setResult(permissionService.deletePermission(name));
        return result;
    }

    @GetMapping()
    public ResponseApi<List<Permission>> getAll() {
        ResponseApi<List<Permission>> result = new ResponseApi<>();
        result.setResult(permissionService.getPermissions());
        return result;
    }

    @PostMapping
    public ResponseApi<PermissionResponseDTO> create(@RequestBody @Valid PermissionRequestDTO request) throws AppException {
        ResponseApi<PermissionResponseDTO> result = new ResponseApi<>();
        result.setResult(permissionService.createPermission(request));
        return result;
    }
}
