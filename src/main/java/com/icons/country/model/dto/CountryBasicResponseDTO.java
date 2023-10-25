package com.icons.country.model.dto;

import com.icons.util.DataListResponseDTO;

public record CountryBasicResponseDTO(
        String id,
        String image,
        String name,
        long population,
        double area,
        DataListResponseDTO icons
) {
}
