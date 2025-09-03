package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.constants.AppValues;
import com.example.db_connect_user_crud.dto.request.LoginRequest;
import com.example.db_connect_user_crud.dto.request.IntrospectRequest;
import com.example.db_connect_user_crud.dto.request.LogoutRequest;
import com.example.db_connect_user_crud.dto.response.LoginResponse;
import com.example.db_connect_user_crud.dto.response.IntrospectResponse;
import com.example.db_connect_user_crud.dto.response.LogoutResponse;
import com.example.db_connect_user_crud.entity.InvalidToken;
import com.example.db_connect_user_crud.entity.Role;
import com.example.db_connect_user_crud.entity.User;
import com.example.db_connect_user_crud.exception.AppException;
import com.example.db_connect_user_crud.mapper.InvalidTokenMapper;
import com.example.db_connect_user_crud.repository.InvalidTokenRepository;
import com.example.db_connect_user_crud.repository.UserRepository;
import com.example.db_connect_user_crud.type.ErrorType;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
  UserRepository repository;
  InvalidTokenRepository invalidTokenRepository;
  InvalidTokenMapper invalidTokenMapper;

  @NonFinal
  @Value(AppValues.SECRET_KEY)
  String SECRET_KEY;

  @NonFinal
  @Value(AppValues.ISSUER)
  String ISSUER;

  public LoginResponse authenticate(LoginRequest request) throws JOSEException {
    User user = repository.findByUsername(request.getUsername())
      .orElseThrow(() -> new AppException(ErrorType.USER_NOT_FOUND));
    PasswordEncoder encoder = new BCryptPasswordEncoder(10);
    boolean isMatched = encoder.matches(request.getPassword(), user.getPassword());

    if (!isMatched) {
      throw new AppException(ErrorType.UNAUTHORIZED);
    }

    String token = generateToken(user);
    return LoginResponse.builder()
      .token(token)
      .build();
  }

  private String generateToken(User user) throws JOSEException {
    Date now = new Date();
    Date exp = new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli());

    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .jwtID(UUID.randomUUID().toString())
      .subject(user.getUsername())
      .claim("scope", buildScope(user.getRoles()))
      .issuer(ISSUER)
      .issueTime(now)
      .expirationTime(exp)
      .build();
    Payload payload = new Payload(claimsSet.toJSONObject());
    JWSObject jwsObject = new JWSObject(header, payload);

    MACSigner signer = new MACSigner(SECRET_KEY);
    jwsObject.sign(signer);
    return jwsObject.serialize();
  }

  private String buildScope(Set<Role> roles) {
    StringJoiner joiner = new StringJoiner(" ");
    if (!roles.isEmpty()) {
      roles.forEach(it -> {
          joiner.add(it.getName());

          if (!it.getPermissions().isEmpty()) {
            it.getPermissions().forEach(perm -> joiner.add(perm.getName()));
          }
        }
      );
    }
    return joiner.toString();
  }

  public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
    String token = request.getToken();
    boolean isValid = true;

    try {
      verifyToken(token);
    } catch (AppException e) {
      isValid = false;
    }

    return IntrospectResponse.builder()
      .isValid(isValid)
      .build();
  }

  public LogoutResponse logout(LogoutRequest request) throws ParseException, JOSEException {
    String token = request.getToken();
    SignedJWT signedJWT = verifyToken(token);
    String jti = signedJWT.getJWTClaimsSet().getJWTID();
    Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();

    InvalidToken invalidToken = InvalidToken.builder()
      .id(jti)
      .exp(exp)
      .build();
    invalidTokenRepository.save(invalidToken);

    return invalidTokenMapper.toResponse(token);
  }

  private SignedJWT verifyToken(String token) throws ParseException, JOSEException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
    boolean isVerified = signedJWT.verify(verifier);
    Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();

    boolean isActive = isVerified && exp.after(new Date());
    if (!isActive) {
      throw new AppException(ErrorType.UNAUTHORIZED);
    }

    boolean isNotValid = invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID());
    if (isNotValid) {
      throw new AppException(ErrorType.UNAUTHORIZED);
    }

    return signedJWT;
  }
}
