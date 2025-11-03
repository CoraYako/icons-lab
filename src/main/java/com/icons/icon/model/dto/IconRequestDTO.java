package com.icons.icon.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IconRequestDTO(
        @NotEmpty @NotBlank String imageURL,
        @NotEmpty @NotBlank String name,
        @NotEmpty @NotBlank String creationDate,
        @NotNull @Min(value = 1) int height,
        String historyDescription,
        @NotEmpty @NotBlank String countryId
) {
}
