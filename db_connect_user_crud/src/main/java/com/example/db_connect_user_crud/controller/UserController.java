package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.dto.request.UserRequest;
import com.example.db_connect_user_crud.model.User;
import com.example.db_connect_user_crud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
  public UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public User createUser(@RequestBody @Valid UserRequest userRequest) {
    return userService.createUser(userRequest);
  }

  @GetMapping("/all")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable String id) {
    return userService.getUserById(id);
  }

  @PutMapping("/{id}")
  public User updateUserById(@PathVariable String id, @RequestBody UserRequest userRequest) {
    return userService.updateUserById(id, userRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteUserById(@PathVariable String id) {
    userService.deleteUserById(id);
  }
}