package com.icons.country.model.dto;

import com.icons.continent.model.dto.ContinentBasicResponseDTO;
import com.icons.util.DataListResponseDTO;

public record CountryResponseDTO(
        String id,
        String image,
        String name,
        long population,
        double area,
        ContinentBasicResponseDTO continent,
        DataListResponseDTO icons
) {
}
