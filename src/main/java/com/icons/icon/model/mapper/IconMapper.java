package com.icons.icon.model.mapper;

import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconBasicResponseDTO;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Lazy;

@Lazy
public interface IconMapper {

    IconEntity toEntity(@NotNull IconRequestDTO dto);

    IconBasicResponseDTO toBasicDTO(@NotNull IconEntity entity);

    IconResponseDTO toCompleteDTO(@NotNull IconEntity entity);
}
