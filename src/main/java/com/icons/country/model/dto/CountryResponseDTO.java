package com.icons.country.model.dto;

import java.util.Set;

public record CountryResponseDTO(
        String id,
        String imageURL,
        String name,
        long population,
        double area,
        String continent,
        Set<String> icons
) {
}
