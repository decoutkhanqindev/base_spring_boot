package com.example.db_connect_user_crud.service;

import com.example.db_connect_user_crud.dto.request.PermissionRequest;
import com.example.db_connect_user_crud.dto.response.PermissionResponse;
import com.example.db_connect_user_crud.entity.Permission;
import com.example.db_connect_user_crud.mapper.PermissionMapper;
import com.example.db_connect_user_crud.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionService {
  PermissionRepository repository;
  PermissionMapper mapper;

  public PermissionResponse create(PermissionRequest request) {
    Permission permission = mapper.toEntity(request);
    return mapper.toResponse(repository.save(permission));
  }

  public List<PermissionResponse> getAll() {
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .toList();
  }

  public PermissionResponse deleteByName(String name) {
    Permission permission = repository.findById(name).orElseThrow();
    repository.deleteById(name);
    return mapper.toResponse(permission);
  }
}
