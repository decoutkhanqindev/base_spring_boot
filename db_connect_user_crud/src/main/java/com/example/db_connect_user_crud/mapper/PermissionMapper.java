package com.example.db_connect_user_crud.mapper;

import com.example.db_connect_user_crud.dto.request.PermissionRequest;
import com.example.db_connect_user_crud.dto.response.PermissionResponse;
import com.example.db_connect_user_crud.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
  Permission toEntity(PermissionRequest request);
  PermissionResponse toResponse(Permission permission);
}
