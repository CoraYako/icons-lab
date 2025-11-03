package com.icons.country.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CountryRequestDTO(
        @NotEmpty @NotBlank String imageURL,
        @NotEmpty @NotBlank String name,
        @NotNull @Min(value = 1) long population,
        @NotNull @Min(value = 1) double area,
        @NotEmpty @NotBlank String continentId
) {
}
