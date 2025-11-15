package com.icons.continent.model.dto;

import java.util.Set;

public record ContinentResponseDTO(
        String id,
        String imageURL,
        String name,
        Set<String> countries
) {
}
