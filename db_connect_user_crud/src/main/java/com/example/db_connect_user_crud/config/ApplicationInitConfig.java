package com.example.db_connect_user_crud.config;

import com.example.db_connect_user_crud.entity.Permission;
import com.example.db_connect_user_crud.entity.Role;
import com.example.db_connect_user_crud.entity.User;
import com.example.db_connect_user_crud.repository.PermissionRepository;
import com.example.db_connect_user_crud.repository.RoleRepository;
import com.example.db_connect_user_crud.repository.UserRepository;
import com.example.db_connect_user_crud.service.RoleService;
import com.example.db_connect_user_crud.type.RoleType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationInitConfig {
  UserRepository userRepository;
  RoleRepository roleRepository;
  PasswordEncoder encoder;

  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {
      HashSet<Role> roles = new HashSet<>(roleRepository.findAllById(List.of(RoleType.ADMIN.name())));

      if (!userRepository.existsByRoles(roles)) {
        String encodedPassword = encoder.encode("admin12345678");

        User user = User.builder()
          .username("admin")
          .password(encodedPassword)
          .email("admin@localhost.com")
          .roles(roles)
          .build();

        userRepository.save(user);
        log.warn("User admin created with password admin12345678, please change it");
      } else {
        log.info("User admin already exists");
      }
    };
  }
}
