package com.example.db_connect_user_crud.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AppException extends RuntimeException {
  AppError error;
}
