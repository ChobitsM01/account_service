package io.reflectoring.accoutService.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserDTO {
    String name;
    String phone;
    String password;
    List<String> roles;
}
