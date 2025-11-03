package com.icons.continent.model.dto;

import com.icons.util.DataListResponseDTO;

public record ContinentResponseDTO(
        String id,
        String imageURL,
        String name,
        DataListResponseDTO countries
) {
}
