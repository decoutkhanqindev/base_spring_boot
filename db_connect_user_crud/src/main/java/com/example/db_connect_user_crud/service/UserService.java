package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.UserCreationRequest;
import com.example.db_connect_user_crud.dto.request.UserUpdateRequest;
import com.example.db_connect_user_crud.dto.response.UserResponse;
import com.example.db_connect_user_crud.entity.User;
import com.example.db_connect_user_crud.exception.AppError;
import com.example.db_connect_user_crud.exception.AppException;
import com.example.db_connect_user_crud.mapper.UserMapper;
import com.example.db_connect_user_crud.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
  UserRepository repository;
  UserMapper mapper;

  public UserResponse createUser(UserCreationRequest request) {
    if (repository.existsByUsername(request.getUsername()))
      throw new AppException(AppError.USER_EXISTS);

    User user = mapper.toEntity(request);
    PasswordEncoder encoder = new BCryptPasswordEncoder(10);
    user.setPassword(encoder.encode(request.getPassword()));

    return mapper.toResponse(repository.save(user));
  }

  public List<UserResponse> getAllUsers() {
    return repository.findAll()
      .stream()
      .map(mapper::toResponse)
      .toList();
  }

  public UserResponse getUserById(String id) {
    return repository.findById(id)
      .map(mapper::toResponse)
      .orElseThrow(() -> new AppException(AppError.USER_NOT_FOUND));
  }

  public UserResponse updateUserById(String id, UserUpdateRequest request) {
    User user = repository.findById(id)
      .orElseThrow(() -> new AppException(AppError.USER_NOT_FOUND));
    mapper.updateEntity(user, request);
    return mapper.toResponse(repository.save(user));
  }

  public UserResponse deleteUserById(String id) {
    User user = repository.findById(id)
      .orElseThrow(() -> new AppException(AppError.USER_NOT_FOUND));
    repository.delete(user);
    return mapper.toResponse(user);
  }
}
