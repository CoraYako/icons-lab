package com.icons.icon.repository;

import com.icons.country.model.CountryEntity;
import com.icons.icon.model.IconEntity;
import com.icons.util.ApiUtils;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpecificationIconFilter {
    public Specification<@NonNull IconEntity> getByFilters(String name, String date, Set<String> countriesName, String order) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(name)) {
                String nameToFind = '%' + name.toLowerCase() + '%';
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), nameToFind));
            }

            if (StringUtils.hasLength(date)) {
                LocalDate localDate = LocalDate.parse(date, ApiUtils.OF_PATTERN);
                predicates.add(criteriaBuilder.equal(root.get("creationDate"), localDate));
            }

            if (!CollectionUtils.isEmpty(countriesName)) {
                Join<CountryEntity, IconEntity> join = root.join("countries", JoinType.INNER);
                Expression<String> countriesId = join.get("id");
                predicates.add(countriesId.in(countriesName));
            }

            query.distinct(true);
            String orderByField = "name";
            query.orderBy(ApiUtils.isASC(order) ?
                    criteriaBuilder.asc(root.get(orderByField)) : criteriaBuilder.desc(root.get(orderByField)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
