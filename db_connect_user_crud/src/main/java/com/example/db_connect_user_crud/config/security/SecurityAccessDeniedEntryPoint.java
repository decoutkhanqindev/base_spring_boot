package com.example.db_connect_user_crud.config.security;

import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.type.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class SecurityAccessDeniedEntryPoint implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
    ApiResponse<?> apiResponse = ApiResponse.builder()
      .isSuccess(false)
      .statusCode(HttpServletResponse.SC_FORBIDDEN)
      .message(ErrorType.FORBIDDEN.getMessage())
      .build();

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
  }
}
