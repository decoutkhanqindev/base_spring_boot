package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.UserRequest;
import com.example.db_connect_user_crud.exception.AppError;
import com.example.db_connect_user_crud.exception.AppException;
import com.example.db_connect_user_crud.model.User;
import com.example.db_connect_user_crud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository repository;

  // Constructor injection
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public User createUser(UserRequest request) {
    if (repository.existsByUsername(request.getUsername()))
      throw new AppException(AppError.USER_EXISTS);

    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    user.setEmail(request.getEmail());
    user.setDob(request.getDob());

    return repository.save(user);
  }

  public List<User> getAllUsers() {
    return repository.findAll();
  }

  public User getUserById(String id) {
    return repository.findById(id)
      .orElseThrow(() -> new AppException(AppError.USER_NOT_FOUND));
  }

  public User updateUserById(String id, UserRequest request) {
    User user = getUserById(id);
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    user.setEmail(request.getEmail());
    user.setDob(request.getDob());

    return repository.save(user);
  }

  public User deleteUserById(String id) {
    User user = getUserById(id);
    repository.deleteById(id);
    return user;
  }
}
