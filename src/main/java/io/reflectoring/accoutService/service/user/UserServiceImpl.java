package io.reflectoring.accoutService.service.user;

import io.reflectoring.accoutService.dto.request.CreateUserDTO;
import io.reflectoring.accoutService.dto.request.UpdateUserDTO;
import io.reflectoring.accoutService.entity.Role;
import io.reflectoring.accoutService.entity.User;
import io.reflectoring.accoutService.exception.AppException;
import io.reflectoring.accoutService.exception.ErrorCode;
import io.reflectoring.accoutService.repository.RoleRepository;
import io.reflectoring.accoutService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createAUser(CreateUserDTO request) throws AppException {
        User existedUser = userRepository.findByPhone(request.getPhone());
        if(existedUser != null){
            throw new AppException(ErrorCode.USER_EXISTS);
        }
        User user = new User();
        user.setName(request.getName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
 /*       HashSet<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);*/
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }

    @Override
    public User findById(int id) throws AppException {
        return userRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllByOrderByIdDesc();
    }

    @Override
    public String deleteUser(int id) throws AppException {
        User existingUser = findById(id);
        if (existingUser == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
        return "Deleted user with id: " + id;
    }

    @Override
    public User updateUser(int id, UpdateUserDTO request) throws AppException {
        User existingUser = findById(id);
        if (existingUser == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if(request.getName()!= null){
            existingUser.setName(request.getName());
        }
        if(request.getPhone() != null){
            existingUser.setPhone(request.getPhone());
        }
        if(request.getPassword() != null){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        List<Role> roles = roleRepository.findAllById(request.getRoles());
        existingUser.setRoles(new HashSet<>(roles));
        return userRepository.save(existingUser);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAllByOrderByIdDesc(pageable);
    }
}
