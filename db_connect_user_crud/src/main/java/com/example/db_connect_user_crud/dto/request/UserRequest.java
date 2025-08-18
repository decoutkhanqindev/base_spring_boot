package com.example.db_connect_user_crud.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserRequest {
  @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
  @NotBlank(message = "Username is required")
  private String username;
  @Size(min = 8, message = "Password must be at least 8 characters")
  @NotBlank(message = "Password is required")
  private String password;
  @Email(message = "Email is not valid")
  @NotBlank(message = "Email is required")
  private String email;
  private LocalDate dob;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }
}
