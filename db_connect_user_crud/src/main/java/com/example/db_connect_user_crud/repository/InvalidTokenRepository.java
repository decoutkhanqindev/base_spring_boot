package com.example.db_connect_user_crud.repository;

import com.example.db_connect_user_crud.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
