package com.icons.icon.repository.filters;

import com.icons.country.model.CountryEntity;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconFilterRequestDTO;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CountryFilterStrategy implements IconFilterStrategy {
    @Override
    public Predicate apply(IconFilterRequestDTO filters, Root<IconEntity> root,
                           CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (!CollectionUtils.isEmpty(filters.countries())) {
            Join<CountryEntity, IconEntity> join = root.join("countries", JoinType.INNER);
            Expression<String> countriesId = join.get("id");
            return countriesId.in(filters.countries());
        }
        return null;
    }
}
