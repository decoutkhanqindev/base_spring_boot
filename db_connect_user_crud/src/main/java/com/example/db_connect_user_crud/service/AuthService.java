package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.AuthRequest;
import com.example.db_connect_user_crud.dto.response.AuthResponse;
import com.example.db_connect_user_crud.entity.User;
import com.example.db_connect_user_crud.exception.AppErrorType;
import com.example.db_connect_user_crud.exception.AppException;
import com.example.db_connect_user_crud.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
  UserRepository repository;

  @NonFinal
  protected static final String SECRET_KEY =
    "cdae3f3fa96c862641885fb903ea23599fe68af0483b932aebfcf1af425900bd122b2f565b4fc46b3ebaed0ff7f49893ed8ee256a99e14540d260e8554997099";
  @NonFinal
  protected static final String ISSUER = "decoutkhanqindev.com";

  public AuthResponse authenticate(AuthRequest request) {
    User user = repository.findByUsername(request.getUsername())
      .orElseThrow(() -> new AppException(AppErrorType.USER_NOT_FOUND));
    PasswordEncoder encoder = new BCryptPasswordEncoder(10);
    boolean isAuthenticated = encoder.matches(request.getPassword(), user.getPassword());

    if (!isAuthenticated) {
      throw new AppException(AppErrorType.UNAUTHORIZED);
    }

    String token = generateToken(user.getUsername());
    return AuthResponse.builder()
      .token(token)
      .build();
  }

  private String generateToken(String username) {
    Date now = new Date();
    Date exp = new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli());

    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .subject(username)
      .issuer(ISSUER)
      .issueTime(now)
      .expirationTime(exp)
      .build();
    Payload payload = new Payload(claimsSet.toJSONObject());
    JWSObject jwsObject = new JWSObject(header, payload);

    try {
      MACSigner signer = new MACSigner(SECRET_KEY);
      jwsObject.sign(signer);
      return jwsObject.serialize();
    } catch (JOSEException e) {
      throw new AppException(AppErrorType.UNKNOWN_ERROR);
    }
  }
}
