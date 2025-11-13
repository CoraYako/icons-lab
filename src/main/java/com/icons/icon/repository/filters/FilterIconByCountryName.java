package com.icons.icon.repository.filters;

import com.icons.country.model.CountryEntity;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconFilterRequestDTO;
import com.icons.shared.AbstractJoinInFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FilterIconByCountryName extends AbstractJoinInFilter<IconEntity, IconFilterRequestDTO, CountryEntity>
        implements IconFilterStrategy {
    @Override
    public Predicate apply(IconFilterRequestDTO filters,
                           Root<IconEntity> root,
                           CriteriaQuery<?> query,
                           CriteriaBuilder criteriaBuilder) {
        return super.apply(filters, root, criteriaBuilder);
    }

    @Override
    protected String getJoinProperty() {
        return "countries";
    }

    @Override
    protected Collection<String> getFilterIds(IconFilterRequestDTO filters) {
        return filters.countries();
    }
}
