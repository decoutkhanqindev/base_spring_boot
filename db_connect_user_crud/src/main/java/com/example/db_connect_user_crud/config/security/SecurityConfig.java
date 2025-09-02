package com.example.db_connect_user_crud.config.security;

import com.example.db_connect_user_crud.constants.*;
import com.example.db_connect_user_crud.type.RoleType;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final String[] AUTH_POST_ENDPOINTS = {
    AuthEndpoints.AUTH + AuthEndpoints.LOGIN,
    AuthEndpoints.AUTH + AuthEndpoints.INTROSPECT
  };

  private final String[] ADMIN_GET_ENDPOINTS = {
    UserEndpoints.USER + UserEndpoints.ALL,
    UserEndpoints.USER + UserEndpoints.USER_ID,
    RoleEndpoints.ROLE + RoleEndpoints.ALL,
    RoleEndpoints.ROLE + RoleEndpoints.ROLE_NAME,
    PermissionEndpoints.PERMISSION + PermissionEndpoints.ALL,
    PermissionEndpoints.PERMISSION + PermissionEndpoints.PERMISSION_NAME
  };

  private final String[] ADMIN_POST_ENDPOINTS = {
    UserEndpoints.USER,
    RoleEndpoints.ROLE,
    PermissionEndpoints.PERMISSION
  };

  private final String[] ADMIN_PUT_ENDPOINTS = {
    UserEndpoints.USER + UserEndpoints.USER_ID,
    RoleEndpoints.ROLE + RoleEndpoints.ROLE_NAME,
    PermissionEndpoints.PERMISSION + PermissionEndpoints.PERMISSION_NAME
  };

  private final String[] ADMIN_DELETE_ENDPOINTS = {
    UserEndpoints.USER + UserEndpoints.USER_ID,
    RoleEndpoints.ROLE + RoleEndpoints.ROLE_NAME,
    PermissionEndpoints.PERMISSION + PermissionEndpoints.PERMISSION_NAME
  };

  @NonFinal
  @Value(AppValues.SECRET_KEY)
  private String SECRET_KEY;

  @Bean
  // define a security filter chain for endpoints
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // configure a security filter chain for endpoints
    http.authorizeHttpRequests(request ->
      request
        .requestMatchers(HttpMethod.POST, AUTH_POST_ENDPOINTS).permitAll()
        .requestMatchers(HttpMethod.GET, ADMIN_GET_ENDPOINTS).hasRole(RoleType.ADMIN.name())
        .requestMatchers(HttpMethod.POST, ADMIN_POST_ENDPOINTS).hasRole(RoleType.ADMIN.name())
        .requestMatchers(HttpMethod.PUT, ADMIN_PUT_ENDPOINTS).hasRole(RoleType.ADMIN.name())
        .requestMatchers(HttpMethod.DELETE, ADMIN_DELETE_ENDPOINTS).hasRole(RoleType.ADMIN.name())
        .anyRequest().authenticated()
    );

    // configure oauth2 resource server for jwt authentication
    http.oauth2ResourceServer(oauth2Configurer ->
      oauth2Configurer
        .jwt(jwtConfigurer ->
          jwtConfigurer
            .decoder(jwtDecoder())
            .jwtAuthenticationConverter(jwtAuthenticationConverter())
        )
        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        .accessDeniedHandler(new JwtAccessDeniedHandler())
    );

    // disable csrf (cross site request forgery)
    http.csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }

  @Bean
  // define a jwt decoder to verify jwt token
  public JwtDecoder jwtDecoder() {
    SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), MacAlgorithm.HS512.name());
    return NimbusJwtDecoder
      .withSecretKey(secretKeySpec)
      .macAlgorithm(MacAlgorithm.HS512)
      .build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

    return jwtAuthenticationConverter;
  }

  @Bean
  // define a password encoder to encode password
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
