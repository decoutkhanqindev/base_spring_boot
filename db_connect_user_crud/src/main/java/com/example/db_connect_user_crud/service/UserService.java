package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.UserCreationRequest;
import com.example.db_connect_user_crud.dto.request.UserUpdateRequest;
import com.example.db_connect_user_crud.dto.response.UserResponse;
import com.example.db_connect_user_crud.entity.Role;
import com.example.db_connect_user_crud.entity.User;
import com.example.db_connect_user_crud.exception.AppException;
import com.example.db_connect_user_crud.mapper.UserMapper;
import com.example.db_connect_user_crud.repository.RoleRepository;
import com.example.db_connect_user_crud.repository.UserRepository;
import com.example.db_connect_user_crud.type.ErrorType;
import com.example.db_connect_user_crud.type.RoleType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
  UserRepository userRepository;
  RoleRepository roleRepository;
  UserMapper userMapper;
  PasswordEncoder passwordEncoder;

  public UserResponse create(UserCreationRequest request) {
    if (userRepository.existsByUsername(request.getUsername()))
      throw new AppException(ErrorType.USER_EXISTS);

    User user = userMapper.toEntity(request);

    String encodedPassword = passwordEncoder.encode(request.getPassword());
    user.setPassword(encodedPassword);

    HashSet<Role> roles = new HashSet<>(roleRepository.findAllById(List.of(RoleType.USER.name())));
    user.setRoles(roles);

    return userMapper.toResponse(userRepository.save(user));
  }

//  @PreAuthorize("hasRole('ADMIN')")
  public List<UserResponse> getAll() {
    return userRepository.findAll()
      .stream()
      .map(userMapper::toResponse)
      .toList();
  }

//  @PostAuthorize("returnObject.username == authentication.name")
  public UserResponse getById(String id) {
    return userRepository.findById(id)
      .map(userMapper::toResponse)
      .orElseThrow(() -> new AppException(ErrorType.USER_NOT_FOUND));
  }

  public UserResponse updateById(String id, UserUpdateRequest request) {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new AppException(ErrorType.USER_NOT_FOUND));

    HashSet<Role> roles = new HashSet<>(roleRepository.findAllById(List.of(RoleType.USER.name())));
    user.setRoles(roles);

    userMapper.updateEntity(user, request);
    return userMapper.toResponse(userRepository.save(user));
  }

  public UserResponse deleteById(String id) {
    User user = userRepository.findById(id)
      .orElseThrow(() -> new AppException(ErrorType.USER_NOT_FOUND));
    userRepository.delete(user);
    return userMapper.toResponse(user);
  }
}
