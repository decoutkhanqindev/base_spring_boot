package com.example.db_connect_user_crud.dto.response;

public class ApiResponse<T> {
  private Boolean success = true;
  private int statusCode = 200;
  private String message = "";
  private T data;

  public ApiResponse() {}

  public ApiResponse(Boolean success, int statusCode, String message, T data) {
    this.success = success;
    this.statusCode = statusCode;
    this.message = message;
    this.data = data;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
