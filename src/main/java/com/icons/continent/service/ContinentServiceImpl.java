package com.icons.continent.service;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.continent.model.dto.ContinentUpdateRequestDTO;
import com.icons.continent.model.mapper.ContinentMapper;
import com.icons.continent.repository.ContinentRepository;
import com.icons.exception.*;
import com.icons.util.ApiUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
@Validated
public class ContinentServiceImpl implements ContinentService {
    private final ContinentMapper continentMapper;
    private final ContinentRepository continentRepository;

    public ContinentServiceImpl(ContinentMapper continentMapper, ContinentRepository continentRepository) {
        this.continentMapper = continentMapper;
        this.continentRepository = continentRepository;
    }

    @Override
    public void createContinent(ContinentRequestDTO dto) {
        if (Objects.isNull(dto))
            throw new NullRequestBodyException("Continent");
        if (Objects.isNull(dto.name()) || dto.name().trim().isEmpty())
            throw new InvalidFieldException("name", "Must provide a continent name");
        if (continentRepository.existsByName(dto.name().trim()))
            throw new DuplicatedResourceException("Continent", dto.name());

        ContinentEntity continent = continentMapper.toEntity(dto);
        continentRepository.save(continent);
    }

    @Override
    public ContinentResponseDTO updateContinent(String id, ContinentUpdateRequestDTO dto) {
        if (Objects.isNull(dto))
            throw new NullRequestBodyException("Continent");
        if (ApiUtils.isNotValidUUID(id))
            throw new InvalidUUIDException(id);
        if (continentRepository.existsByName(dto.name()))
            throw new DuplicatedResourceException("Continent", dto.name());

        ContinentEntity continent = continentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Continent", id));

        var continentUpdated = applyUpdates(continent, dto);

        continentUpdated = continentRepository.save(continentUpdated);
        return continentMapper.toDTO(continentUpdated);
    }

    @Override
    public Page<@NonNull ContinentResponseDTO> listContinents(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, ApiUtils.ELEMENTS_PER_PAGE);

        return continentRepository.findAll(pageable).map(continentMapper::toDTO);
    }

    @Override
    public ContinentResponseDTO getContinentById(String id) {
        return continentMapper.toDTO(getById(id));
    }

    @Override
    public ContinentEntity getById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new InvalidUUIDException(id);
        return continentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Continent", id));
    }

    private ContinentEntity applyUpdates(ContinentEntity continent, ContinentUpdateRequestDTO dto) {
        if (dto.name() != null && !dto.name().trim().isEmpty())
            continent.setName(dto.name());

        if (dto.imageURL() != null && !dto.imageURL().trim().isEmpty())
            continent.setImageURL(dto.imageURL());

        return continent;
    }
}
