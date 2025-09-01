package com.example.db_connect_user_crud.exception;

import com.example.db_connect_user_crud.type.ErrorType;
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
  ErrorType error;
}
