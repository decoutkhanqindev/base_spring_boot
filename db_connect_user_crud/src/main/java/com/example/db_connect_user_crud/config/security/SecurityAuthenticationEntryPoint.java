package com.example.db_connect_user_crud.config.security;

import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.type.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    ApiResponse<?> apiResponse = ApiResponse.builder()
      .isSuccess(false)
      .statusCode(HttpServletResponse.SC_UNAUTHORIZED)
      .message(ErrorType.UNAUTHORIZED.getMessage())
      .build();

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
  }
}
