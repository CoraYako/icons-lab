package com.icons.country.service;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryFilterRequestDTO;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import com.icons.country.model.dto.CountryUpdateRequestDTO;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;

public interface CountryService {

    CountryResponseDTO createCountry(@NotNull CountryRequestDTO dto);

    CountryResponseDTO updateCountry(@NotNull String id, @NotNull CountryUpdateRequestDTO dto);

    Page<@NonNull CountryResponseDTO> listCountries(CountryFilterRequestDTO filters);

    void deleteCountry(@NotNull String id);

    CountryResponseDTO getCountryById(@NotNull String id);

    CountryEntity getById(@NotNull String id);
}
