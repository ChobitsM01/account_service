package io.reflectoring.accoutService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@Table(name = "expired_token")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpiredToken {
    @Id
    String id;
    Date expiredTime;
}
