package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.UserRequest;
import com.example.db_connect_user_crud.model.User;
import com.example.db_connect_user_crud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;

  // Constructor injection
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(UserRequest userRequest) {
    if (userRepository.existsByUsername(userRequest.getUsername()))
      throw new RuntimeException("User already exists");

    User user = new User();
    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    user.setEmail(userRequest.getEmail());
    user.setDob(userRequest.getDob());

    return userRepository.save(user);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(String id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("User not found"));
  }

  public User updateUserById(String id, UserRequest userRequest) {
    User user = getUserById(id);
    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    user.setEmail(userRequest.getEmail());
    user.setDob(userRequest.getDob());

    return userRepository.save(user);
  }

  public void deleteUserById(String id) {
    userRepository.deleteById(id);
  }
}
