package com.icons.country.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CountryRequestDTO(
        String image,
        @NotEmpty @NotBlank String name,
        @NotNull @Min(value = 0) long population,
        @NotNull @Min(value = 2) double area,
        @NotEmpty @NotBlank String continentId
) {
}
