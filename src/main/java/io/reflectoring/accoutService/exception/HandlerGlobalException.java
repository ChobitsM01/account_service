package io.reflectoring.accoutService.exception;

import io.reflectoring.accoutService.dto.response.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerGlobalException {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseApi> runtimeExceptionHandler(RuntimeException e) {
        ResponseApi responseApi = new ResponseApi();
        responseApi.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getErrorCode());
        responseApi.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(responseApi);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseApi> appExceptionHandler(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ResponseApi responseApi = new ResponseApi();
        responseApi.setMessage(errorCode.getMessage());
        responseApi.setStatusCode(errorCode.getErrorCode());
        return ResponseEntity.badRequest().body(responseApi);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseApi> appExceptionHandler(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.USER_UNAUTHENTICATED;
        ResponseApi responseApi = new ResponseApi();
        responseApi.setMessage(errorCode.getMessage());
        responseApi.setStatusCode(errorCode.getErrorCode());
        return ResponseEntity.badRequest().body(responseApi);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ResponseApi> validException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex){

        }

        ResponseApi responseApi = new ResponseApi();

        responseApi.setStatusCode(errorCode.getErrorCode());
        responseApi.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(responseApi);
    }
}
