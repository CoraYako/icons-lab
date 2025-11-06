package com.icons;

import jakarta.persistence.criteria.Predicate;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpecificationFilter<E, F extends BaseFilterRequestDTO, S extends FilterStrategy<E, F>> {
    private final List<S> filterStrategies;

    protected AbstractSpecificationFilter(List<S> filterStrategies) {
        this.filterStrategies = filterStrategies;
    }

    public Specification<@NonNull E> getByFilters(F filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filterStrategies.forEach(strategy -> {
                Predicate predicate = strategy.apply(filters, root, query, criteriaBuilder);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            });

            query.distinct(true);
            String DEFAULT_ORDER = "ID";
            query.orderBy(StringUtils.hasLength(filters.orderByField()) ?
                    criteriaBuilder.asc(root.get(filters.orderByField())) :
                    criteriaBuilder.asc(root.get(DEFAULT_ORDER)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
