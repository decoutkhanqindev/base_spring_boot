package com.example.db_connect_user_crud.exception;


import com.example.db_connect_user_crud.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
  @ExceptionHandler(value = RuntimeException.class)
  ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException e) {
    ApiResponse<String> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setStatusCode(500);
    response.setMessage(e.getMessage());
    response.setData(null);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse<String>> handleValidation(MethodArgumentNotValidException e) {
    String message = e.getFieldError().getDefaultMessage();
    AppError error = AppError.valueOf(message);

    ApiResponse<String> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setStatusCode(error.getStatusCode());
    response.setMessage(error.getMessage());
    response.setData(null);
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse<String>> handleAppException(AppException e) {
    ApiResponse<String> response = new ApiResponse<>();
    response.setSuccess(false);
    response.setStatusCode(e.getError().getStatusCode());
    response.setMessage(e.getMessage());
    response.setData(null);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}