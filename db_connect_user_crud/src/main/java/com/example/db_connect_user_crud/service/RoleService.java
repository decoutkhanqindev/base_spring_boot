package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.RoleRequest;
import com.example.db_connect_user_crud.dto.response.PermissionResponse;
import com.example.db_connect_user_crud.dto.response.RoleResponse;
import com.example.db_connect_user_crud.entity.Permission;
import com.example.db_connect_user_crud.entity.Role;
import com.example.db_connect_user_crud.mapper.RoleMapper;
import com.example.db_connect_user_crud.repository.PermissionRepository;
import com.example.db_connect_user_crud.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService {
  RoleRepository roleRepository;
  PermissionRepository permissionRepository;
  RoleMapper roleMapper;

  public RoleResponse create(RoleRequest request) {
    Role role = roleMapper.toEntity(request);

    List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
    role.setPermissions(new HashSet<>(permissions));

    return roleMapper.toResponse(roleRepository.save(role));
  }

  public List<RoleResponse> getAll() {
    return roleRepository.findAll().stream()
      .map(roleMapper::toResponse)
      .toList();
  }

  public RoleResponse deleteByName(String name) {
    Role role = roleRepository.findById(name).orElseThrow();
    roleRepository.deleteById(name);
    return roleMapper.toResponse(role);
  }
}
