package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.UserRequest;
import com.example.db_connect_user_crud.entity.User;
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

  public User addUser(UserRequest userRequest) {
    User user = new User();
    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    user.setEmail(userRequest.getEmail());
    user.setDob(userRequest.getDob());

    return userRepository.save(user);
  }

  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  public User getUser(String id) {
    return userRepository.findById(id).get();
  }

  public User updateUser(String id, UserRequest userRequest) {
    User user = getUser(id);
    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    user.setEmail(userRequest.getEmail());
    user.setDob(userRequest.getDob());

    return userRepository.save(user);
  }

  public void deleteUser(String id) {
    userRepository.deleteById(id);
  }
}
