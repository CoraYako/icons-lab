package com.icons.icon.model.dto;

import com.icons.util.DataListResponseDTO;

public record IconResponseDTO(
        String id,
        String image,
        String name,
        String creationDate,
        int height,
        String historyDescription,
        DataListResponseDTO countries
) {
}
