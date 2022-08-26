package com.icons.repository.spec;

import com.icons.dto.filters.IconFiltersDTO;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class IconSpecification {
    public Specification<IconEntity> getByFilters(IconFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("denomination")), "%" + filtersDTO.getName().toLowerCase() + "%"));
            }

            if (StringUtils.hasLength(filtersDTO.getDate())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(filtersDTO.getDate(), formatter);

                predicates.add(criteriaBuilder.equal(root.<LocalDate>get("creation"), localDate));
            }

            if (!CollectionUtils.isEmpty(filtersDTO.getCountries())) {
                Join<CountryEntity, IconEntity> join = root.join("countries", JoinType.INNER);
                Expression<String> countriesId = join.get("id");
                predicates.add(countriesId.in(filtersDTO.getCountries()));
            }

            query.distinct(true);

            String orderByField = "denomination";
            query.orderBy(filtersDTO.isASC() ? criteriaBuilder.asc(root.get(orderByField)) : criteriaBuilder.desc(root.get(orderByField)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
