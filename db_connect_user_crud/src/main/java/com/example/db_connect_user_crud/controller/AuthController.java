package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.constants.AuthEndpoints;
import com.example.db_connect_user_crud.dto.request.AuthRequest;
import com.example.db_connect_user_crud.dto.request.IntrospectRequest;
import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.dto.response.AuthResponse;
import com.example.db_connect_user_crud.dto.response.IntrospectResponse;
import com.example.db_connect_user_crud.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthEndpoints.AUTH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
  AuthService service;

  @PostMapping(AuthEndpoints.LOGIN)
  public ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest request) {
    AuthResponse response = service.authenticate(request);
    return ApiResponse.<AuthResponse>builder()
      .data(response)
      .build();
  }

  @PostMapping(AuthEndpoints.INTROSPECT)
  public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
    IntrospectResponse response = service.introspect(request);
    return ApiResponse.<IntrospectResponse>builder()
      .data(response)
      .build();
  }
}
