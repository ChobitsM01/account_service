package io.reflectoring.accoutService.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequestDTO {
    String name;
    String description;
    Set<String> permissions;
}