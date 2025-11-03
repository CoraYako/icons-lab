package com.icons.country.model.dto;

import com.icons.util.DataListResponseDTO;

public record CountryResponseDTO(
        String id,
        String imageURL,
        String name,
        long population,
        double area,
        String continent,
        DataListResponseDTO icons
) {
}
