package com.example.db_connect_user_crud.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserUpdateRequest {
  @Size(min = 8, message = "PASSWORD_INVALID")
  @NotBlank(message = "PASSWORD_NOT_BLANK")
  String password;
  @Email(message = "EMAIL_INVALID")
  @NotBlank(message = "EMAIL_NOT_BLANK")
  String email;
  LocalDate dob;
  Set<String> roles;
}