package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.dto.request.UserRequest;
import com.example.db_connect_user_crud.entity.User;
import com.example.db_connect_user_crud.service.UserService;
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
  public User addUser(@RequestBody UserRequest userRequest) {
    return userService.addUser(userRequest);
  }

  @GetMapping
  public List<User> getAllUser() {
    return userService.getAllUser();
  }

  @GetMapping("/{id}")
  public User getUser(@PathVariable String id) {
    return userService.getUser(id);
  }

  @PutMapping("/{id}")
  public User updateUser(
      @PathVariable String id,
      @RequestBody UserRequest userRequest
  ) {
    return userService.updateUser(id, userRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
  }
}