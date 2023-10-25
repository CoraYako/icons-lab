package com.icons.country.model.dto;

import com.icons.continent.model.dto.ContinentBasicResponseDTO;

public record CountryBasicInfoResponseDTO(
        String id,
        String image,
        String name,
        long population,
        double area,
        ContinentBasicResponseDTO continent
) {
}
