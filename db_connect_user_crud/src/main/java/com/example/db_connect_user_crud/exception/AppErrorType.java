package com.example.db_connect_user_crud.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
@ToString
public enum AppErrorType {
  UNKNOWN_ERROR(500, "An unexpected error occurred"),
  USERNAME_INVALID(400, "Username must be between 3 and 20 characters"),
  PASSWORD_INVALID(400, "Password must be at least 8 characters"),
  EMAIL_INVALID(400, "Email is not valid"),
  USERNAME_NOT_BLANK(400, "Username is required"),
  PASSWORD_NOT_BLANK(400, "Password is required"),
  EMAIL_NOT_BLANK(400, "Email is required"),
  USER_EXISTS(409, "User already exists"),
  USER_NOT_FOUND(401, "User not found"),
  UNAUTHORIZED(401, "Unauthorized user")
  ;

  int statusCode;
  String message;
}
