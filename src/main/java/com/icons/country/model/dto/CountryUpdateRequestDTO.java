package com.icons.country.model.dto;

public record CountryUpdateRequestDTO(
        String imageURL,
        String name,
        long population,
        double area,
        String continentId
) {
}
