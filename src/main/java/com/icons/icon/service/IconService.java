package com.icons.icon.service;

import com.icons.country.model.CountryEntity;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconUpdateRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;

@Lazy
public interface IconService {

    void createIcon(@NotNull IconRequestDTO dto);

    IconResponseDTO updateIcon(@NotNull String id, @NotNull IconUpdateRequestDTO dto);

    IconEntity updateIcon(@NotNull String id, @NotNull CountryEntity country);

    IconResponseDTO getIconDTOById(@NotNull String id);

    IconEntity getIconById(@NotNull String id);

    void deleteIcon(String id);

    Page<IconResponseDTO> listIcons(int pageNumber);
}
