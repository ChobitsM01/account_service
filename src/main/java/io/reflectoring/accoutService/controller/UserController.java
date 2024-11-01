package io.reflectoring.accoutService.controller;

import io.reflectoring.accoutService.dto.request.CreateUserDTO;
import io.reflectoring.accoutService.dto.request.UpdateUserDTO;
import io.reflectoring.accoutService.dto.response.ResponseApi;
import io.reflectoring.accoutService.entity.User;
import io.reflectoring.accoutService.exception.AppException;
import io.reflectoring.accoutService.service.user.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseApi<User> createUser(@RequestBody @Valid CreateUserDTO request) throws AppException {
        ResponseApi<User> result = new ResponseApi<>();
        result.setResult(userService.createAUser(request));
        return result;
    }

    @PreAuthorize("hasAuthority('GET_USER')")
    @GetMapping("/{id}")
    public ResponseApi<User> getUserById(@PathVariable int id) throws AppException {
        ResponseApi<User> result = new ResponseApi<>();
        result.setResult(userService.findById(id));
        return result;
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseApi<List<User>> findAllUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User name: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info( grantedAuthority.getAuthority()));
        ResponseApi<List<User>> result = new ResponseApi<>();
        result.setResult(userService.findAllUsers());
        return result;
    }

    @PutMapping("/{id}")
    public ResponseApi<User> updateUser(@PathVariable int id,@RequestBody UpdateUserDTO request) throws AppException {
        ResponseApi<User> result = new ResponseApi<>();
        result.setResult(userService.updateUser(id, request));
        return result;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseApi<String> deleteUser(@PathVariable int id) throws AppException {
        ResponseApi<String> result = new ResponseApi<>();
        result.setResult(userService.deleteUser(id));
        return result;
    }

    @GetMapping("/users")
    public ResponseApi<Page<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> users = userService.getUsers(PageRequest.of(page, size));
        ResponseApi<Page<User>> responseAPI = new ResponseApi<>();
        responseAPI.setResult(users);
        return responseAPI;
    }

}
