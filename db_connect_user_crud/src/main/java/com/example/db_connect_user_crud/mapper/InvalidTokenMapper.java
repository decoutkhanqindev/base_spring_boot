package com.example.db_connect_user_crud.mapper;

import com.example.db_connect_user_crud.dto.request.LogoutRequest;
import com.example.db_connect_user_crud.dto.response.LogoutResponse;
import com.example.db_connect_user_crud.entity.InvalidToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvalidTokenMapper {
  LogoutResponse toResponse(String invalidToken);
}
