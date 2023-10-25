package com.icons.country.service;

import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryUpdateRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import com.icons.country.model.CountryEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface CountryService {

    void createCountry(@NotNull CountryRequestDTO dto);

    CountryResponseDTO updateCountry(@NotNull String id, @NotNull CountryUpdateRequestDTO dto);

    Page<CountryResponseDTO> listCountries(int pageNumber);

    void deleteCountry(@NotNull String id);

    CountryResponseDTO getCountryDTOById(@NotNull String id);

    CountryEntity getCountryById(@NotNull String id);
}
