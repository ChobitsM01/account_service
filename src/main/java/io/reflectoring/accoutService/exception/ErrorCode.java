package io.reflectoring.accoutService.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(400, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTS(400,"User is already exist", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(400,"User not found",HttpStatus.NOT_FOUND),
    USERNAME_INVALID(400, "User name must greater than 7 character and less than 16 character", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(400, "Phone must be 10 or 11 character", HttpStatus.BAD_REQUEST),
    INVALID_KEY(400, "Uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(400, "Password must greater than 7 character and less than 16 character", HttpStatus.BAD_REQUEST),
    USER_UNAUTHENTICATED(400, "User unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(400, "You don't have permission to access this resource",HttpStatus.FORBIDDEN),
    PERMISSION_NOT_FOUND(400,"User not found",HttpStatus.NOT_FOUND),;

    ErrorCode(final int errorCode, final String message,HttpStatusCode httpStatusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
    private int errorCode;
    private String message;
    private HttpStatusCode httpStatusCode;
}
