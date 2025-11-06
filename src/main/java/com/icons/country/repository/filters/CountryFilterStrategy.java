package com.icons.country.repository.filters;

import com.icons.FilterStrategy;
import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryFilterRequestDTO;


public interface CountryFilterStrategy extends FilterStrategy<CountryEntity, CountryFilterRequestDTO> {
}