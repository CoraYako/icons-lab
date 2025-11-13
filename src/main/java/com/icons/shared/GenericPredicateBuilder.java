package com.icons.shared;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class GenericPredicateBuilder {
    public Predicate build(Root<?> root, CriteriaBuilder criteriaBuilder, String columnName, String columnValue) {
        if (columnValue == null || columnValue.trim().isEmpty()) {
            return null;
        }

        columnValue = '%' + columnValue.toLowerCase() + '%';
        return criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)), columnValue);
    }
}