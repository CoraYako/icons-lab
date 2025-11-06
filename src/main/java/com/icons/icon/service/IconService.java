package com.icons.icon.service;

import com.icons.icon.model.dto.IconFilterRequestDTO;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import com.icons.icon.model.dto.IconUpdateRequestDTO;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;

public interface IconService {

    void createIcon(@NotNull IconRequestDTO dto);

    IconResponseDTO updateIcon(@NotNull String id, @NotNull IconUpdateRequestDTO dto);

    IconResponseDTO getIconById(@NotNull String id);

    void deleteIcon(String id);

    Page<@NonNull IconResponseDTO> listIcons(IconFilterRequestDTO filters);
}
