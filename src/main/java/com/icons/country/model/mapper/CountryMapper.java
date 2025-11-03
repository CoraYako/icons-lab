package com.icons.country.model.mapper;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import jakarta.validation.constraints.NotNull;

public interface CountryMapper {

    CountryEntity toEntity(@NotNull CountryRequestDTO dto);

    CountryResponseDTO toDTO(@NotNull CountryEntity entity);
}
