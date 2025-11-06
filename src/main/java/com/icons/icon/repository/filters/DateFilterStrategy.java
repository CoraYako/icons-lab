package com.icons.icon.repository.filters;

import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconFilterRequestDTO;
import com.icons.util.ApiUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Component
public class DateFilterStrategy implements IconFilterStrategy {
    @Override
    public Predicate apply(IconFilterRequestDTO filters, Root<IconEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.hasLength(filters.date())) {
            LocalDate localDate = LocalDate.parse(filters.date(), ApiUtils.OF_PATTERN);
            return criteriaBuilder.equal(root.get("creationDate"), localDate);
        }
        return null;
    }
}
