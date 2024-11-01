package io.reflectoring.accoutService.service.user;

import io.reflectoring.accoutService.dto.request.CreateUserDTO;
import io.reflectoring.accoutService.dto.request.UpdateUserDTO;
import io.reflectoring.accoutService.entity.User;
import io.reflectoring.accoutService.exception.AppException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User createAUser(CreateUserDTO user) throws AppException;
    User findById(int id) throws AppException;
    List<User> findAllUsers();
    String deleteUser(int id) throws AppException;
    User updateUser(int id, UpdateUserDTO request) throws AppException;
    Page<User> getUsers(Pageable pageable);
}
