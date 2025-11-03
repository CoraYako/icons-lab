package com.icons.icon.model.mapper;

import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import jakarta.validation.constraints.NotNull;

public interface IconMapper {

    IconEntity toEntity(@NotNull IconRequestDTO dto);

    IconResponseDTO toDTO(@NotNull IconEntity entity);
}
