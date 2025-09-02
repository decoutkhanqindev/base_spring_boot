package com.example.db_connect_user_crud.repository;

import com.example.db_connect_user_crud.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
