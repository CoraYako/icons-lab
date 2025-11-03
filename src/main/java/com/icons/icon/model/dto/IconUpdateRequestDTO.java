package com.icons.icon.model.dto;

import java.util.Set;

public record IconUpdateRequestDTO(
        String imageURL,
        String name,
        String creationDate,
        int height,
        String historyDescription,
        Set<String> countriesId
) {
}
