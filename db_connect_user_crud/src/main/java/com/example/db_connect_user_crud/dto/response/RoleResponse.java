package com.example.db_connect_user_crud.dto.response;

import com.example.db_connect_user_crud.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RoleResponse {
  String name;
  String description;
  Set<PermissionResponse> permissions;
}
