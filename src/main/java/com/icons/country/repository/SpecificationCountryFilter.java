package com.icons.country.repository;

import com.icons.country.model.CountryEntity;
import com.icons.icon.model.IconEntity;
import com.icons.util.ApiUtils;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpecificationCountryFilter {
    public Specification<CountryEntity> getByFilters(String name, String continentName,
                                                     Set<String> iconsNames, String order) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String value;

            if (StringUtils.hasLength(name)) {
                value = '%' + name.toLowerCase() + '%';
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), value));
            }

            if (StringUtils.hasLength(continentName)) {
                value = '%' + continentName.toLowerCase() + '%';
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("continent")), value));
            }

            if (!CollectionUtils.isEmpty(iconsNames)) {
                Join<IconEntity, CountryEntity> join = root.join("icons", JoinType.INNER);
                Expression<String> iconsId = join.get("id");
                predicates.add(iconsId.in(iconsNames));
            }

            query.distinct(true);
            String orderByField = "name";
            query.orderBy(ApiUtils.isASC(order) ?
                    criteriaBuilder.asc(root.get(orderByField)) : criteriaBuilder.desc(root.get(orderByField)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
