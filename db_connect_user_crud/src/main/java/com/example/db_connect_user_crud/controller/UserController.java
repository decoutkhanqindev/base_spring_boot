package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.constants.UserEndpoints;
import com.example.db_connect_user_crud.dto.request.UserCreationRequest;
import com.example.db_connect_user_crud.dto.request.UserUpdateRequest;
import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.dto.response.UserResponse;
import com.example.db_connect_user_crud.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserEndpoints.USER)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
  UserService service;

  @PostMapping
  public ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request) {
    UserResponse response = service.create(request);
    return ApiResponse.<UserResponse>builder()
      .data(response)
      .build();
  }

  @GetMapping(UserEndpoints.ALL)
  public ApiResponse<List<UserResponse>> getAll() {
    List<UserResponse> response = service.getAll();
    return ApiResponse.<List<UserResponse>>builder()
      .data(response)
      .build();
  }

  @GetMapping(UserEndpoints.USER_ID)
  public ApiResponse<UserResponse> getById(@PathVariable String id) {
    UserResponse response = service.getById(id);
    return ApiResponse.<UserResponse>builder()
      .data(response)
      .build();
  }

  @PutMapping(UserEndpoints.USER_ID)
  public ApiResponse<UserResponse> updateById(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request) {
    UserResponse response = service.updateById(id, request);
    return ApiResponse.<UserResponse>builder()
      .data(response)
      .build();
  }

  @DeleteMapping(UserEndpoints.USER_ID)
  public ApiResponse<UserResponse> deleteById(@PathVariable String id) {
    UserResponse response = service.deleteById(id);
    return ApiResponse.<UserResponse>builder()
      .data(response)
      .build();
  }
}