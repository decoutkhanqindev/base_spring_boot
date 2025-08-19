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
    ApiResponse<User> response = new ApiResponse<>();
    response.setData(user);
    return response;
  }

  @GetMapping("/all")
  public ApiResponse<List<User>> getAllUsers() {
    List<User> users = service.getAllUsers();
    ApiResponse<List<User>> response = new ApiResponse<>();
    response.setData(users);
    return response;
  }

  @GetMapping("/{id}")
  public ApiResponse<User> getUserById(@PathVariable String id) {
    User user = service.getUserById(id);
    ApiResponse<User> response = new ApiResponse<>();
    response.setData(user);
    return response;
  }

  @PutMapping("/{id}")
  public ApiResponse<User> updateUserById(@PathVariable String id, @RequestBody UserRequest request) {
    User user = service.updateUserById(id, request);
    ApiResponse<User> response = new ApiResponse<>();
    response.setData(user);
    return response;
  }

  @DeleteMapping("/{id}")
  public ApiResponse<User> deleteUserById(@PathVariable String id) {
    User user = service.deleteUserById(id);
    ApiResponse<User> response = new ApiResponse<>();
    response.setData(user);
    return response;
  }
}