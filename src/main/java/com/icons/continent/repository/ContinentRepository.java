package com.icons.continent.repository;

import com.icons.continent.model.ContinentEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContinentRepository extends JpaRepository<@NonNull ContinentEntity, @NonNull String> {

    boolean existsByName(String denomination);
}
