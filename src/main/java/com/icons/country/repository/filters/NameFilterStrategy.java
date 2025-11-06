package com.icons.country.repository.filters;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryFilterRequestDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NameFilterStrategy implements CountryFilterStrategy {
    @Override
    public Predicate apply(CountryFilterRequestDTO filters, Root<CountryEntity> root,
                           CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.hasLength(filters.countryName())) {
            String value = '%' + filters.countryName().toLowerCase() + '%';
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), value);
        }
        return null;
    }
}