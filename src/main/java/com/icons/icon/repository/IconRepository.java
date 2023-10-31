package com.icons.icon.repository;

import com.icons.icon.model.IconEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IconRepository extends JpaRepository<IconEntity, String>, JpaSpecificationExecutor<IconEntity> {

    boolean existsByName(String name);
}
