package com.example.db_connect_user_crud.exception;

public enum AppError {
  USERNAME_INVALID(400, "Username must be between 3 and 20 characters"),
  PASSWORD_INVALID(400, "Password must be at least 8 characters"),
  EMAIL_INVALID(400, "Email is not valid"),
  USERNAME_NOT_BLANK(400, "Username is required"),
  PASSWORD_NOT_BLANK(400, "Password is required"),
  EMAIL_NOT_BLANK(400, "Email is required"),
  USER_EXISTS(409, "User already exists"),
  USER_NOT_FOUND(401, "User not found"),
  ;

  private int statusCode;
  private String message;

  AppError(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
}
