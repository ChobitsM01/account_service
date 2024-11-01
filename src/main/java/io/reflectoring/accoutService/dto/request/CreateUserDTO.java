package io.reflectoring.accoutService.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserDTO {
    @Size(min = 8, max = 15, message = "USERNAME_INVALID")
    String name;
    @Size(min = 10, max = 11, message = "PHONE_INVALID")
    String phone;
    @Size(min = 8, max = 15, message = "INVALID_PASSWORD")
    String password;
}
