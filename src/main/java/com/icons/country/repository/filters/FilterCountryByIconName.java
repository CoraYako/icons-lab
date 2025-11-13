package com.icons.country.repository.filters;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryFilterRequestDTO;
import com.icons.icon.model.IconEntity;
import com.icons.shared.AbstractJoinInFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FilterCountryByIconName extends AbstractJoinInFilter<CountryEntity, CountryFilterRequestDTO, IconEntity>
        implements CountryFilterStrategy {
    @Override
    public Predicate apply(CountryFilterRequestDTO filters,
                           Root<CountryEntity> root,
                           CriteriaQuery<?> query,
                           CriteriaBuilder criteriaBuilder) {
        return super.apply(filters, root, criteriaBuilder);
    }

    @Override
    protected String getJoinProperty() {
        return "icons";
    }

    @Override
    protected Collection<String> getFilterIds(CountryFilterRequestDTO filters) {
        return filters.iconsNames();
    }
}
