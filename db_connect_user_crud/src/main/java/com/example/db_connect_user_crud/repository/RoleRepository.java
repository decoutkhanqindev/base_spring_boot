package com.example.db_connect_user_crud.repository;

import com.example.db_connect_user_crud.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
