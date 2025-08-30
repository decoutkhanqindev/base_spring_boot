package com.example.db_connect_user_crud.config;

import com.example.db_connect_user_crud.type.RoleType;
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
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  // public endpoints are allowed to be accessed without authentication
  private final String[] PUBLIC_ENDPOINTS = {"/user", "/auth/login", "/auth/introspect"};

  // admin endpoints are allowed to be accessed only with admin roles
  private final String[] ADMIN_ENDPOINTS = {"/user/all", "user/{id}"};

  @Value("${jwt.secret-key}")
  private String SECRET_KEY;

  @Bean
  // define a security filter chain for endpoints
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // configure a security filter chain for endpoints
    http.authorizeHttpRequests(request ->
      request
        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
        .permitAll()
        .requestMatchers(HttpMethod.GET, ADMIN_ENDPOINTS)
//        .hasRole(RoleType.ADMIN.name())
        .hasAnyAuthority("SCOPE_" + RoleType.ADMIN.name())
        .anyRequest()
        .authenticated()
    );

    // configure oauth2 resource server for jwt authentication
    http.oauth2ResourceServer(oauth2Configurer ->
      oauth2Configurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
    );

    // disable csrf (cross site request forgery)
    http.csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }

  @Bean
  // define a jwt decoder to verify jwt token
  public JwtDecoder jwtDecoder() {
    SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HS512");
    return NimbusJwtDecoder
      .withSecretKey(secretKeySpec)
      .macAlgorithm(MacAlgorithm.HS512)
      .build();
  }

  @Bean
  // define a password encoder to encode password
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
