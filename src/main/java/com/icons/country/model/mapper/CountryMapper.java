package com.icons.country.model.mapper;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryBasicInfoResponseDTO;
import com.icons.country.model.dto.CountryBasicResponseDTO;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Lazy;

@Lazy
public interface CountryMapper {

    CountryEntity toEntity(@NotNull CountryRequestDTO dto);

    CountryBasicResponseDTO toBasicDTO(@NotNull CountryEntity entity);

    CountryBasicInfoResponseDTO toBasicInfoDTO(@NotNull CountryEntity entity);

    CountryResponseDTO toCompleteDTO(@NotNull CountryEntity entity);
}
