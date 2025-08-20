package com.example.db_connect_user_crud.mapper;

import com.example.db_connect_user_crud.dto.request.UserCreationRequest;
import com.example.db_connect_user_crud.dto.request.UserUpdateRequest;
import com.example.db_connect_user_crud.dto.response.UserResponse;
import com.example.db_connect_user_crud.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User toEntity(UserCreationRequest request);
  UserResponse toResponse(User user);
  void updateEntity(@MappingTarget User user, UserUpdateRequest request);
}
