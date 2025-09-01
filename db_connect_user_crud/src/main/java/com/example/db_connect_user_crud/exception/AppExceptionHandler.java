package com.example.db_connect_user_crud.exception;

import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.type.ErrorType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
  @ExceptionHandler(value = RuntimeException.class)
  ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException e) {
    ErrorType error = ErrorType.UNKNOWN_ERROR;
    ApiResponse<String> response = ApiResponse.<String>builder()
      .isSuccess(false)
      .statusCode(error.getStatusCode())
      .message(error.getMessage())
      .data(null)
      .build();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse<String>> handleValidation(MethodArgumentNotValidException e) {
    String message = e.getFieldError().getDefaultMessage();
    ErrorType error = ErrorType.valueOf(message);
    ApiResponse<String> response = ApiResponse.<String>builder()
      .isSuccess(false)
      .statusCode(error.getStatusCode())
      .message(error.getMessage())
      .data(null)
      .build();
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse<String>> handleAppException(AppException e) {
    ErrorType error = e.getError();
    ApiResponse<String> response = ApiResponse.<String>builder()
      .isSuccess(false)
      .statusCode(error.getStatusCode())
      .message(error.getMessage())
      .data(null)
      .build();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}