package com.icons.country.repository.filters;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryFilterRequestDTO;
import com.icons.shared.GenericPredicateBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class FilterCountryByContinentName implements CountryFilterStrategy {
    private final GenericPredicateBuilder genericPredicateBuilder;

    public FilterCountryByContinentName(GenericPredicateBuilder genericPredicateBuilder) {
        this.genericPredicateBuilder = genericPredicateBuilder;
    }

    @Override
    public Predicate apply(CountryFilterRequestDTO filters,
                           Root<CountryEntity> root,
                           CriteriaQuery<?> query,
                           CriteriaBuilder criteriaBuilder) {
        final String COLUMN_NAME = "continent";
        return genericPredicateBuilder.build(root, criteriaBuilder, COLUMN_NAME, filters.continentName());
    }
}
