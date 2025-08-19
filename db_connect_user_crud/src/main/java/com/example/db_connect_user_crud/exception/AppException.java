package com.example.db_connect_user_crud.exception;

public class AppException extends RuntimeException {
  private AppError error;

  public AppException(AppError error) {
    super(error.getMessage());
    this.error = error;
  }

  public AppError getError() {
    return error;
  }

  public void setError(AppError error) {
    this.error = error;
  }
}
