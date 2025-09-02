package com.example.db_connect_user_crud.repository;

import com.example.db_connect_user_crud.entity.Role;
import com.example.db_connect_user_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  boolean existsByUsername(String username);
  boolean existsByRoles(Set<Role> roles);
  Optional<User> findByUsername(String username);
}
