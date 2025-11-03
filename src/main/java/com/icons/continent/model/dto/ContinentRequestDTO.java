package com.icons.continent.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ContinentRequestDTO(
        @NotEmpty @NotBlank String imageURL,
        @NotBlank @NotEmpty String name
) {
}
