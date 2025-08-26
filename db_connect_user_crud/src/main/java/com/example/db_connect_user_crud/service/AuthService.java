package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.AuthRequest;
import com.example.db_connect_user_crud.entity.User;
import com.example.db_connect_user_crud.exception.AppError;
import com.example.db_connect_user_crud.exception.AppException;
import com.example.db_connect_user_crud.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
  UserRepository repository;

  public boolean authenticate(AuthRequest request) {
    User user = repository.findByUsername(request.getUsername())
      .orElseThrow(() -> new AppException(AppError.USER_NOT_FOUND));
    PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    if (!encoder.matches(request.getPassword(), user.getPassword())) {
      throw new AppException(AppError.UNAUTHORIZED);
    } else {
      return true;
    }
  }
}
