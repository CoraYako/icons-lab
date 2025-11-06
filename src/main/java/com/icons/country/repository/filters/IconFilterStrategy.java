package com.icons.country.repository.filters;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryFilterRequestDTO;
import com.icons.icon.model.IconEntity;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class IconFilterStrategy implements CountryFilterStrategy {
    @Override
    public Predicate apply(CountryFilterRequestDTO filters, Root<CountryEntity> root,
                           CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (!CollectionUtils.isEmpty(filters.iconsNames())) {
            Join<IconEntity, CountryEntity> join = root.join("icons", JoinType.INNER);
            Expression<String> iconsId = join.get("id");
            return iconsId.in(filters.iconsNames());
        }
        return null;
    }
}
