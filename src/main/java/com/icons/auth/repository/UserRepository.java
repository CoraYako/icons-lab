package com.icons.auth.repository;

import com.icons.auth.model.UserEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<@NonNull UserEntity, @NonNull String> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
