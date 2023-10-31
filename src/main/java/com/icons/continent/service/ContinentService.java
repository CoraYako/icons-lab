package com.icons.continent.service;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.continent.model.dto.ContinentUpdateRequestDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface ContinentService {

    void createContinent(@NotNull ContinentRequestDTO dto);

    ContinentResponseDTO updateContinent(@NotNull String id, @NotNull ContinentUpdateRequestDTO dto);

    Page<ContinentResponseDTO> listContinents(int pageNumber);

    ContinentResponseDTO getContinentDTOById(@NotNull String id);

    ContinentEntity getContinentById(@NotNull String id);
}
