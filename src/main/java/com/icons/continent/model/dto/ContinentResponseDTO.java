package com.icons.continent.model.dto;

import com.icons.util.DataListResponseDTO;

public record ContinentResponseDTO(
        String id,
        String image,
        String name,
        DataListResponseDTO countries
) {
}
