package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.constants.PermissionEndpoints;
import com.example.db_connect_user_crud.dto.request.PermissionRequest;
import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.dto.response.PermissionResponse;
import com.example.db_connect_user_crud.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PermissionEndpoints.PERMISSION)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
  PermissionService service;

  @PostMapping()
  public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
    PermissionResponse response = service.create(request);
    return ApiResponse.<PermissionResponse>builder()
      .data(response)
      .build();
  }

  @GetMapping(PermissionEndpoints.ALL)
  public ApiResponse<List<PermissionResponse>> getAll() {
    List<PermissionResponse> response = service.getAll();
    return ApiResponse.<List<PermissionResponse>>builder()
      .data(response)
      .build();
  }

  @DeleteMapping(PermissionEndpoints.PERMISSION_NAME)
  public ApiResponse<PermissionResponse> deleteByName(@PathVariable String name) {
    PermissionResponse response = service.deleteByName(name);
    return ApiResponse.<PermissionResponse>builder()
      .data(response)
      .build();
  }
}
