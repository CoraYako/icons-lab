package com.icons.repository.spec;

import com.icons.dto.filters.CountryFiltersDTO;
import com.icons.entity.ContinentEntity;
import com.icons.entity.CountryEntity;
import com.icons.entity.IconEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CountrySpecification {
    public Specification<CountryEntity> getByFilters(CountryFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("denomination")), "%" + filtersDTO.getName().toLowerCase() + "%"));
            }

            if (StringUtils.hasLength(filtersDTO.getContinent())) {
                Join<ContinentEntity, CountryEntity> join = root.join("continent", JoinType.LEFT);
                Expression<String> continentId = join.get("id");
                predicates.add(continentId.in(filtersDTO.getContinent()));
            }

            if (!CollectionUtils.isEmpty(filtersDTO.getIcons())) {
                Join<IconEntity, CountryEntity> join = root.join("icons", JoinType.INNER);
                Expression<String> iconsId = join.get("id");
                predicates.add(iconsId.in(filtersDTO.getIcons()));
            }

            query.distinct(true);

            String orderByField = "denomination";
            query.orderBy(filtersDTO.isASC() ? criteriaBuilder.asc(root.get(orderByField)) : criteriaBuilder.desc(root.get(orderByField)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
