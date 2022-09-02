package com.icons.repository;

import com.icons.entity.CountryEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, String> {

    @Query(value = "SELECT * FROM countries WHERE denomination LIKE :denomination AND deleted = false", nativeQuery = true)
    Optional<CountryEntity> findByName(@Param("denomination") String denomination);

    List<CountryEntity> findAll(Specification<CountryEntity> spec);
}
