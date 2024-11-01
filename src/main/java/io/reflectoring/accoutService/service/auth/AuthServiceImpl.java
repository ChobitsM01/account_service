package io.reflectoring.accoutService.service.auth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.reflectoring.accoutService.dto.request.AuthRequestDTO;
import io.reflectoring.accoutService.dto.request.LogoutRequestDTO;
import io.reflectoring.accoutService.dto.request.ValidTokenRequestDTO;
import io.reflectoring.accoutService.dto.response.AuthResponseDTO;
import io.reflectoring.accoutService.dto.response.ValidTokenResponseDTO;
import io.reflectoring.accoutService.entity.ExpiredToken;
import io.reflectoring.accoutService.entity.User;
import io.reflectoring.accoutService.exception.AppException;
import io.reflectoring.accoutService.exception.ErrorCode;
import io.reflectoring.accoutService.repository.ExpiredTokenRepository;
import io.reflectoring.accoutService.repository.UserRepository;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpiredTokenRepository expiredTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public ValidTokenResponseDTO validateToken(ValidTokenRequestDTO request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        ValidTokenResponseDTO response = new ValidTokenResponseDTO();
        try{
            verifyToken(token);
        } catch (AppException e) {
            isValid = false;
        }
        response.setValid(isValid);
        return response;
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO request) throws AppException {
        User isExistedUser = userRepository.findByPhone(request.getPhone());
        if (isExistedUser == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), isExistedUser.getPassword());
        if (!isAuthenticated) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }
        var token = generateJWTToken(isExistedUser);
        AuthResponseDTO result = new AuthResponseDTO();
        result.setToken(token);
        result.setAuthenticated(isAuthenticated);
        return result;
    }

    private String generateJWTToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getPhone())
                .issueTime(new Date())
                .expirationTime(
                        new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())
                )
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(
                    role -> {
                        stringJoiner.add("ROLE_" + role.getName());
                        if(!CollectionUtils.isEmpty(role.getPermissions())){
                            role.getPermissions().forEach(
                                    permission -> stringJoiner.add(permission.getName())
                            );
                        }
                    }
            );
        }
        return stringJoiner.toString();
    }

    public SignedJWT verifyToken(String token) throws ParseException, JOSEException, AppException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if(!(verified && expiration.after(new Date())))
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        if(expiredTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        return signedJWT;
    }

    public String logout(LogoutRequestDTO requestDTO) throws AppException, ParseException, JOSEException {
        var signToken = verifyToken(requestDTO.getToken());
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expirationTime = signToken.getJWTClaimsSet().getExpirationTime();
        ExpiredToken expiredToken = new ExpiredToken();
        expiredToken.setExpiredTime(expirationTime);
        expiredToken.setId(jit);
        expiredTokenRepository.save(expiredToken);
        return "Logout successful!";
    }
}
