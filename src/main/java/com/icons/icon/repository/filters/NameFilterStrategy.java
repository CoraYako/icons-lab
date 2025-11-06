package com.icons.icon.repository.filters;

import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconFilterRequestDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NameFilterStrategy implements IconFilterStrategy {
    @Override
    public Predicate apply(IconFilterRequestDTO filters, Root<IconEntity> root,
                           CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.hasLength(filters.name())) {
            String nameToFind = '%' + filters.name().toLowerCase() + '%';
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), nameToFind);
        }
        return null;
    }
}
