package com.icons.continent.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ContinentRequestDTO(
        String image,
        @NotBlank @NotEmpty String name
) {
}
