package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.constants.AuthEndpoints;
import com.example.db_connect_user_crud.dto.request.LoginRequest;
import com.example.db_connect_user_crud.dto.request.IntrospectRequest;
import com.example.db_connect_user_crud.dto.request.LogoutRequest;
import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.dto.response.LoginResponse;
import com.example.db_connect_user_crud.dto.response.IntrospectResponse;
import com.example.db_connect_user_crud.dto.response.LogoutResponse;
import com.example.db_connect_user_crud.service.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(AuthEndpoints.AUTH)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
  AuthService service;

  @PostMapping(AuthEndpoints.LOGIN)
  public ApiResponse<LoginResponse> authenticate(@RequestBody LoginRequest request) throws JOSEException {
    LoginResponse response = service.authenticate(request);
    return ApiResponse.<LoginResponse>builder()
      .data(response)
      .build();
  }

  @PostMapping(AuthEndpoints.INTROSPECT)
  public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
    IntrospectResponse response = service.introspect(request);
    return ApiResponse.<IntrospectResponse>builder()
      .data(response)
      .build();
  }

  @PostMapping(AuthEndpoints.LOGOUT)
  public ApiResponse<LogoutResponse> logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException {
    LogoutResponse response = service.logout(request);
    return ApiResponse.<LogoutResponse>builder()
      .data(response)
      .build();
  }
}
