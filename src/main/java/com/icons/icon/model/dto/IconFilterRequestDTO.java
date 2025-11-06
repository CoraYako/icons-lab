package com.icons.icon.model.dto;

import com.icons.BaseFilterRequestDTO;

import java.util.Set;

public record IconFilterRequestDTO(
        int pageNumber,
        String name,
        String date,
        Set<String> countries,
        String orderByField
        ) implements BaseFilterRequestDTO {
}
