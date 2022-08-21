package com.icons.repository;

import com.icons.entity.ContinentEntity;
import com.icons.entity.IconEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IconRepository extends JpaRepository<IconEntity, String> {

    @Query(value = "SELECT * FROM icons WHERE denomination LIKE :denomination", nativeQuery = true)
    Optional<ContinentEntity> findByName(@Param("denomination") String denomination);
}
