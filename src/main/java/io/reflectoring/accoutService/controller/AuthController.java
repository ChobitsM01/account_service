package io.reflectoring.accoutService.controller;

import com.nimbusds.jose.JOSEException;
import io.reflectoring.accoutService.dto.request.AuthRequestDTO;
import io.reflectoring.accoutService.dto.request.LogoutRequestDTO;
import io.reflectoring.accoutService.dto.request.ValidTokenRequestDTO;
import io.reflectoring.accoutService.dto.response.AuthResponseDTO;
import io.reflectoring.accoutService.dto.response.ResponseApi;
import io.reflectoring.accoutService.dto.response.ValidTokenResponseDTO;
import io.reflectoring.accoutService.exception.AppException;
import io.reflectoring.accoutService.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    ResponseApi<AuthResponseDTO> authen(@RequestBody AuthRequestDTO request) throws AppException {
        ResponseApi<AuthResponseDTO> result = new ResponseApi<>();
        result.setResult(authService.authenticate(request));
        return result;
    }

    @PostMapping("/verify")
    ResponseApi<ValidTokenResponseDTO> authen1(@RequestBody ValidTokenRequestDTO request) throws ParseException, JOSEException, AppException {
        ResponseApi<ValidTokenResponseDTO> result = new ResponseApi<>();
        result.setResult(authService.validateToken(request));
        return result;
    }

    @PostMapping("/logout")
    ResponseApi<String> logout(@RequestBody LogoutRequestDTO request) throws AppException, ParseException, JOSEException {
        ResponseApi<String> result = new ResponseApi<>();
        result.setResult(authService.logout(request));
        return result;
    }
}
