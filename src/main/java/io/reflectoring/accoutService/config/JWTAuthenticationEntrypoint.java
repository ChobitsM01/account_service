package io.reflectoring.accoutService.config;

import io.reflectoring.accoutService.dto.response.ResponseApi;
import io.reflectoring.accoutService.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JWTAuthenticationEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.USER_UNAUTHENTICATED;
        response.setStatus(errorCode.getErrorCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResponseApi responseApi = new ResponseApi();
        responseApi.setStatusCode(errorCode.getErrorCode());
        responseApi.setMessage(errorCode.getMessage());
        response.getWriter().print(responseApi);
        response.flushBuffer();
    }
}
