package com.example.db_connect_user_crud.config;

import com.example.db_connect_user_crud.entity.User;
import com.example.db_connect_user_crud.repository.UserRepository;
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

@Slf4j
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationInitConfig {
  PasswordEncoder encoder;

  @Bean
  public ApplicationRunner applicationRunner(UserRepository repository) {
    return args -> {
      HashSet<String> roles = new HashSet<>();
      roles.add(RoleType.ADMIN.name());

      if (!repository.existsByRoles(roles)) {
        String encodedPassword = encoder.encode("admin12345678");

        User user = User.builder()
          .username("admin")
          .password(encodedPassword)
          .email("admin@localhost.com")
          .roles(roles)
          .build();

        repository.save(user);
        log.warn("User admin created with password admin12345678, please change it");
      } else {
        log.info("User admin already exists");
      }
    };
  }
}
