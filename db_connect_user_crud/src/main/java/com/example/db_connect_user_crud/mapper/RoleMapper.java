package com.example.db_connect_user_crud.mapper;

import com.example.db_connect_user_crud.dto.request.PermissionRequest;
import com.example.db_connect_user_crud.dto.request.RoleRequest;
import com.example.db_connect_user_crud.dto.response.PermissionResponse;
import com.example.db_connect_user_crud.dto.response.RoleResponse;
import com.example.db_connect_user_crud.entity.Permission;
import com.example.db_connect_user_crud.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  @Mapping(target = "permissions", ignore = true)
  Role toEntity(RoleRequest request);
  RoleResponse toResponse(Role role);
}
