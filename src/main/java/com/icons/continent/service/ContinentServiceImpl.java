package com.icons.continent.service;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.continent.model.dto.ContinentUpdateRequestDTO;
import com.icons.continent.model.mapper.ContinentMapper;
import com.icons.continent.repository.ContinentRepository;
import com.icons.util.ApiUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.InvalidParameterException;
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
            throw new NullPointerException("The provided Continent is null or invalid.");
        if (Objects.isNull(dto.name()) || dto.name().trim().isEmpty())
            throw new InvalidParameterException("Must provide a valid Country name.");
        if (continentRepository.existsByName(dto.name().trim()))
            throw new EntityExistsException("Trying to create an existent Continent.");
        ContinentEntity continent = continentMapper.toEntity(dto);
        continentRepository.save(continent);
    }

    @Override
    public ContinentResponseDTO updateContinent(String id, ContinentUpdateRequestDTO dto) {
        if (Objects.isNull(dto))
            throw new NullPointerException("The current Continent is null. Can't be updated.");
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        if (continentRepository.existsByName(dto.name()))
            throw new IllegalArgumentException(
                    String.format("There is already a Continent with the provided name %s", dto.name()));
        ContinentEntity continent = continentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Continent not found for ID: %s", id)));
        continent.setName(dto.name());
        continent.setImage(dto.image());
        return continentMapper.toCompleteDTO(continentRepository.save(continent));
    }

    @Override
    public Page<ContinentResponseDTO> listContinents(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, ApiUtils.ELEMENTS_PER_PAGE);
        pageable.next().getPageNumber();
        return continentRepository.findAll(pageable).map(continentMapper::toCompleteDTO);
    }

    @Override
    public ContinentResponseDTO getContinentDTOById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided id value doesn't represents a valid UUID.");
        return continentRepository.findById(id).map(continentMapper::toCompleteDTO)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Continent not found for ID: %s", id)));
    }

    @Override
    public ContinentEntity getContinentById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        return continentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("The Continent with ID %s does not exists", id))
                );
    }
}
