package io.reflectoring.accoutService.service.auth;

import com.nimbusds.jose.JOSEException;
import io.reflectoring.accoutService.dto.request.AuthRequestDTO;
import io.reflectoring.accoutService.dto.request.LogoutRequestDTO;
import io.reflectoring.accoutService.dto.request.ValidTokenRequestDTO;
import io.reflectoring.accoutService.dto.response.AuthResponseDTO;
import io.reflectoring.accoutService.dto.response.ValidTokenResponseDTO;
import io.reflectoring.accoutService.exception.AppException;

import java.text.ParseException;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO request) throws AppException;
    ValidTokenResponseDTO validateToken(ValidTokenRequestDTO request) throws JOSEException, ParseException, AppException;
    String logout(LogoutRequestDTO requestDTO) throws AppException, ParseException, JOSEException;
}
