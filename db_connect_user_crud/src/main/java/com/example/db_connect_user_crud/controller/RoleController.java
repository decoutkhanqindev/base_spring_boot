package com.example.db_connect_user_crud.controller;

import com.example.db_connect_user_crud.constants.RoleEndpoints;
import com.example.db_connect_user_crud.dto.request.RoleRequest;
import com.example.db_connect_user_crud.dto.response.ApiResponse;
import com.example.db_connect_user_crud.dto.response.RoleResponse;
import com.example.db_connect_user_crud.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RoleEndpoints.ROLE)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
  RoleService service;

  @PostMapping()
  public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
    RoleResponse response = service.create(request);
    return ApiResponse.<RoleResponse>builder()
      .data(response)
      .build();
  }

  @GetMapping(RoleEndpoints.ALL)
  public ApiResponse<List<RoleResponse>> getAll() {
    List<RoleResponse> response = service.getAll();
    return ApiResponse.<List<RoleResponse>>builder()
      .data(response)
      .build();
  }

  @DeleteMapping(RoleEndpoints.ROLE_NAME)
  public ApiResponse<RoleResponse> deleteByName(@PathVariable String name) {
    RoleResponse response = service.deleteByName(name);
    return ApiResponse.<RoleResponse>builder()
      .data(response)
      .build();
  }
}
