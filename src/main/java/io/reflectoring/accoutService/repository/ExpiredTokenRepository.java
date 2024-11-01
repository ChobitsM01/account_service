package io.reflectoring.accoutService.repository;

import io.reflectoring.accoutService.entity.ExpiredToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiredTokenRepository extends JpaRepository<ExpiredToken, String> {
}
