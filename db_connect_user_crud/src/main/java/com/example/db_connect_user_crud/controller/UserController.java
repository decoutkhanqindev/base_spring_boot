package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.dto.request.UserRequest;
import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.model.User;
import com.example.db_connect_user_crud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
  public UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @PostMapping
  public ApiResponse<User> createUser(@RequestBody @Valid UserRequest request) {
    User user = service.createUser(request);
    return ApiResponse.<User>builder()
      .data(user)
      .build();
  }

  @GetMapping("/all")
  public ApiResponse<List<User>> getAllUsers() {
    List<User> users = service.getAllUsers();
    return ApiResponse.<List<User>>builder()
      .data(users)
      .build();
  }

  @GetMapping("/{id}")
  public ApiResponse<User> getUserById(@PathVariable String id) {
    User user = service.getUserById(id);
    return ApiResponse.<User>builder()
      .data(user)
      .build();
  }

  @PutMapping("/{id}")
  public ApiResponse<User> updateUserById(@PathVariable String id, @RequestBody UserRequest request) {
    User user = service.updateUserById(id, request);
    return ApiResponse.<User>builder()
      .data(user)
      .build();
  }

  @DeleteMapping("/{id}")
  public ApiResponse<User> deleteUserById(@PathVariable String id) {
    User user = service.deleteUserById(id);
    return ApiResponse.<User>builder()
      .data(user)
      .build();
  }
}