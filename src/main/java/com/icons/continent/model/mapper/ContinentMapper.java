package com.icons.continent.model.mapper;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import jakarta.validation.constraints.NotNull;

public interface ContinentMapper {

    ContinentEntity toEntity(@NotNull ContinentRequestDTO dto);

    ContinentResponseDTO toDTO(@NotNull ContinentEntity entity);
}
