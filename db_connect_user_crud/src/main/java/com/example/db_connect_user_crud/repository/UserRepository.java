package com.example.db_connect_user_crud.repository;

import com.example.db_connect_user_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  //  JpaRepository<User, String> -> User - Entity and String is ID type
  boolean existsByUsername(String username);
}
