package com.icons.icon.repository;

import com.icons.icon.model.IconEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IconRepository extends JpaRepository<@NonNull IconEntity, @NonNull String>,
        JpaSpecificationExecutor<@NonNull IconEntity> {

    boolean existsByName(String name);
}
