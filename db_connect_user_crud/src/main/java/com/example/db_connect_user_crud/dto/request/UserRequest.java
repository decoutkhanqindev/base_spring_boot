package com.example.db_connect_user_crud.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserRequest {
  @Size(min = 3, max = 20, message =  "USERNAME_INVALID")
  @NotBlank(message = "USERNAME_NOT_BLANK")
  private String username;

  @Size(min = 8, message = "PASSWORD_INVALID")
  @NotBlank(message = "PASSWORD_NOT_BLANK")
  private String password;

  @Email(message = "EMAIL_INVALID")
  @NotBlank(message = "EMAIL_NOT_BLANK")
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
