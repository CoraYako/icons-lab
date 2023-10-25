package com.icons.country.model.dto;

import java.util.Set;

public record CountryUpdateRequestDTO(
        String image,
        String name,
        long population,
        double area,
        String continentId,
        Set<String> iconsId
) {
}
