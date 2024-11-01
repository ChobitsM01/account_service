package io.reflectoring.accoutService.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseApi<T> {
    private int statusCode = 200;
    private String message;
    private T result;
}
