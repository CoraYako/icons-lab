package com.icons.icon.repository.filters;

import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconFilterRequestDTO;
import com.icons.shared.GenericPredicateBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class FilterIconByIconName implements IconFilterStrategy {
    private final GenericPredicateBuilder genericPredicateBuilder;

    public FilterIconByIconName(GenericPredicateBuilder genericPredicateBuilder) {
        this.genericPredicateBuilder = genericPredicateBuilder;
    }

    @Override
    public Predicate apply(IconFilterRequestDTO filters,
                           Root<IconEntity> root,
                           CriteriaQuery<?> query,
                           CriteriaBuilder criteriaBuilder) {
        final String COLUMN_NAME = "name";
        return genericPredicateBuilder.build(root, criteriaBuilder, COLUMN_NAME, filters.name());
    }
}
