package com.example.db_connect_user_crud.controller;

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
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
  UserService service;

  @PostMapping
  public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
    UserResponse response = service.createUser(request);
    return ApiResponse.<UserResponse>builder()
      .data(response)
      .build();
  }

  @GetMapping("/all")
  public ApiResponse<List<UserResponse>> getAllUsers() {
    List<UserResponse> response = service.getAllUsers();
    return ApiResponse.<List<UserResponse>>builder()
      .data(response)
      .build();
  }

  @GetMapping("/{id}")
  public ApiResponse<UserResponse> getUserById(@PathVariable String id) {
    UserResponse response = service.getUserById(id);
    return ApiResponse.<UserResponse>builder()
      .data(response)
      .build();
  }

  @PutMapping("/{id}")
  public ApiResponse<UserResponse> updateUserById(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request) {
    UserResponse response = service.updateUserById(id, request);
    return ApiResponse.<UserResponse>builder()
      .data(response)
      .build();
  }

  @DeleteMapping("/{id}")
  public ApiResponse<UserResponse> deleteUserById(@PathVariable String id) {
    UserResponse response = service.deleteUserById(id);
    return ApiResponse.<UserResponse>builder()
      .data(response)
      .build();
  }
}