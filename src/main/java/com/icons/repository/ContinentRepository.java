package com.icons.repository;

import com.icons.entity.ContinentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContinentRepository extends JpaRepository<ContinentEntity, String> {

    @Query(value = "SELECT * FROM continents WHERE denomination LIKE :denomination", nativeQuery = true)
    Optional<ContinentEntity> findByName(@Param("denomination") String denomination);
}
