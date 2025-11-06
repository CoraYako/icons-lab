package com.icons;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface FilterStrategy<E, F extends BaseFilterRequestDTO> {
    Predicate apply(F filters,
                    Root<E> root,
                    CriteriaQuery<?> query,
                    CriteriaBuilder criteriaBuilder);
}
