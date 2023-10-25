package com.icons.icon.model.dto;

public record IconBasicResponseDTO(
        String id,
        String image,
        String name,
        String creationDate,
        int height,
        String historyDescription
) {
}
