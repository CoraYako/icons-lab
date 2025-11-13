package com.icons.country.model.dto;

import com.icons.shared.BaseFilterRequestDTO;

import java.util.Set;

public record CountryFilterRequestDTO(
        int pageNumber,
        String countryName,
        String continentName,
        Set<String> iconsNames,
        String orderByField
) implements BaseFilterRequestDTO {
}