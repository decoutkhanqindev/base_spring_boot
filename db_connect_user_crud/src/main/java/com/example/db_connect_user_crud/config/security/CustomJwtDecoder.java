package com.example.db_connect_user_crud.config.security;

import com.example.db_connect_user_crud.constants.AppValues;
import com.example.db_connect_user_crud.dto.request.IntrospectRequest;
import com.example.db_connect_user_crud.dto.response.IntrospectResponse;
import com.example.db_connect_user_crud.service.AuthService;
import com.example.db_connect_user_crud.type.ErrorType;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
  AuthService service;

  @NonFinal
  @Value(AppValues.SECRET_KEY)
  private String SECRET_KEY;

  @Override
  public Jwt decode(String token) throws JwtException {
    try {
      IntrospectRequest request = IntrospectRequest.builder()
        .token(token)
        .build();
      IntrospectResponse response = service.introspect(request);

      if (!response.isValid()) {
        throw new JwtException("Invalid token");
      }
    } catch (ParseException | JOSEException e) {
      throw new RuntimeException(e);
    }

    SecretKeySpec secretKey = new SecretKeySpec(
      SECRET_KEY.getBytes(),
      MacAlgorithm.HS512.getName()
    );

    NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder
      .withSecretKey(secretKey)
      .macAlgorithm(MacAlgorithm.HS512)
      .build();

    return nimbusJwtDecoder.decode(token);
  }
}
