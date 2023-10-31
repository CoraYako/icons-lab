package com.icons.country.repository;

import com.icons.country.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CountryRepository extends JpaRepository<CountryEntity, String>, JpaSpecificationExecutor<CountryEntity> {

    boolean existsByName(String name);
}
