package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.dto.request.AuthRequest;
import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.dto.response.AuthResponse;
import com.example.db_connect_user_crud.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthController {
  AuthService service;

  @PostMapping("/login")
  public ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest request) {
    AuthResponse response = AuthResponse.builder()
      .authenticated(service.authenticate(request))
      .build();

    return ApiResponse.<AuthResponse>builder()
      .data(response)
      .build();
  }
}
