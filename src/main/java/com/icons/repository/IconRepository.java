package com.icons.repository;

import com.icons.entity.IconEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IconRepository extends JpaRepository<IconEntity, String>, JpaSpecificationExecutor<IconEntity> {

    @Query(value = "SELECT * FROM icons WHERE denomination LIKE :denomination", nativeQuery = true)
    Optional<IconEntity> findByName(@Param("denomination") String denomination);

    List<IconEntity> findAll(Specification<IconEntity> spec);
}
