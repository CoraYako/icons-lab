package com.icons.continent.repository;

import com.icons.continent.model.ContinentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContinentRepository extends JpaRepository<ContinentEntity, String> {

    boolean existsByName(String denomination);
}
