package com.icons.icon.model.dto;

import java.util.Set;

public record IconUpdateRequestDTO(
        String image,
        String name,
        String creationDate,
        int height,
        String historyDescription,
        Set<String> countriesId
) {
}
