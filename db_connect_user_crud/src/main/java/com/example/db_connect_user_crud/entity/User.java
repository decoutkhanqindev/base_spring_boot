package com.example.db_connect_user_crud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private Long id;
  private String username;
  private String password;
  private String email;
  private LocalDateTime dob;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public LocalDateTime getDob() {
    return dob;
  }

  public void setDob(LocalDateTime dob) {
    this.dob = dob;
  }
}
