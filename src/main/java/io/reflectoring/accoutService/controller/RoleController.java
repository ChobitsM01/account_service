package io.reflectoring.accoutService.controller;

import io.reflectoring.accoutService.dto.request.RoleRequestDTO;
import io.reflectoring.accoutService.dto.response.ResponseApi;
import io.reflectoring.accoutService.entity.Role;
import io.reflectoring.accoutService.exception.AppException;
import io.reflectoring.accoutService.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseApi<Role> create(@RequestBody RoleRequestDTO roleRequestDTO) {
        ResponseApi<Role> result = new ResponseApi<>();
        result.setResult(roleService.createRole(roleRequestDTO));
        return result;
    }

    @GetMapping()
    public ResponseApi<List<Role>> getAll() {
        ResponseApi<List<Role>> result = new ResponseApi<>();
        result.setResult(roleService.getAllRoles());
        return result;
    }

    @DeleteMapping("/{name}")
    public ResponseApi<String> delete(@PathVariable String name) throws AppException {
        ResponseApi<String> result = new ResponseApi<>();
        result.setResult(roleService.deleteRole(name));
        return result;
    }
}
