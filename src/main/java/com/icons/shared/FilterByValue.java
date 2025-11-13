package com.icons.shared;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class FilterByValue {
    public Predicate build(Root<?> root, CriteriaBuilder criteriaBuilder, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        value = '%' + value.toLowerCase() + '%';
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), value);
    }
}