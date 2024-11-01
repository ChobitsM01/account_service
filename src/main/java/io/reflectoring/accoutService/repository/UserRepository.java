package io.reflectoring.accoutService.repository;

import io.reflectoring.accoutService.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByOrderByIdDesc(Pageable pageable);
    List<User> findAllByOrderByIdDesc();
    User findByPhone(String phone);
}
