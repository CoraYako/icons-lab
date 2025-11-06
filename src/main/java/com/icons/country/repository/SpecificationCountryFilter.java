package com.icons.country.repository;

import com.icons.AbstractSpecificationFilter;
import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryFilterRequestDTO;
import com.icons.country.repository.filters.CountryFilterStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecificationCountryFilter extends
        AbstractSpecificationFilter<CountryEntity, CountryFilterRequestDTO, CountryFilterStrategy> {

    protected SpecificationCountryFilter(List<CountryFilterStrategy> filterStrategies) {
        super(filterStrategies);
    }
}
