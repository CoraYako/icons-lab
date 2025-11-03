package com.icons.country.repository;

import com.icons.country.model.CountryEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CountryRepository extends JpaRepository<@NonNull CountryEntity, @NonNull String>,
        JpaSpecificationExecutor<@NonNull CountryEntity> {

    boolean existsByName(String name);
}
