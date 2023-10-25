package com.icons.country.repository;

import com.icons.country.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {

    boolean existsByName(String name);

    Optional<CountryEntity> findByName(String name);
}
