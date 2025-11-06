package com.icons.icon.service;

import com.icons.country.model.CountryEntity;
import com.icons.country.service.CountryService;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconFilterRequestDTO;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import com.icons.icon.model.dto.IconUpdateRequestDTO;
import com.icons.icon.model.mapper.IconMapper;
import com.icons.icon.repository.IconRepository;
import com.icons.icon.repository.SpecificationIconFilter;
import com.icons.util.ApiUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Validated
public class IconServiceImpl implements IconService {
    private final IconRepository iconRepository;
    private final IconMapper iconMapper;
    private final CountryService countryService;
    private final SpecificationIconFilter specificationIconFilter;

    public IconServiceImpl(IconRepository iconRepository, IconMapper iconMapper,
                           CountryService countryService, SpecificationIconFilter specificationIconFilter) {
        this.iconRepository = iconRepository;
        this.iconMapper = iconMapper;
        this.countryService = countryService;
        this.specificationIconFilter = specificationIconFilter;
    }

    @Override
    public void createIcon(IconRequestDTO dto) {
        if (Objects.isNull(dto))
            throw new IllegalArgumentException("The provided Geographical Icon is invalid or null.");
        if (Objects.isNull(dto.name()) || dto.name().trim().isEmpty())
            throw new IllegalArgumentException("The provided Icon name is invalid or null.");
        if (iconRepository.existsByName(dto.name()))
            throw new EntityExistsException(String.format("An Icon is already created for name %s.", dto.name()));

        IconEntity icon = iconMapper.toEntity(dto);
        CountryEntity country = countryService.getById(dto.countryId());
        icon.getCountries().add(country);
        iconRepository.save(icon);
    }

    @Override
    public IconResponseDTO updateIcon(String id, IconUpdateRequestDTO dto) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        if (Objects.isNull(dto))
            throw new IllegalArgumentException("The provided Icon is invalid or null.");

        IconEntity icon = iconRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Icon not found for ID %s", id))
        );

        icon.setImageURL(dto.imageURL());
        icon.setName(dto.name());
        icon.setHeight(dto.height());
        icon.setHistoryDescription(dto.historyDescription());

        if (!Objects.isNull(dto.creationDate()) && !dto.creationDate().trim().isEmpty())
            icon.setCreationDate(LocalDate.parse(dto.creationDate(), ApiUtils.OF_PATTERN));

        if (!Objects.isNull(dto.countriesId()) && !dto.countriesId().isEmpty()) {
            List<String> countriesId = icon.getCountries().stream().map(CountryEntity::getId).toList();
            List<String> filteredCountriesId = dto.countriesId()
                    .stream()
                    .filter(countryId -> !countriesId.contains(countryId))
                    .toList();
            icon.getCountries().addAll(
                    filteredCountriesId
                            .stream()
                            .map(countryService::getById)
                            .toList()
            );
        }

        return iconMapper.toDTO(iconRepository.save(icon));
    }

    @Override
    public IconResponseDTO getIconById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        return iconRepository.findById(id)
                .map(iconMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Icon not found for ID %s", id)));
    }

    @Override
    public void deleteIcon(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        IconEntity icon = iconRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Icon not found for ID %s", id)));
        iconRepository.delete(icon);
    }

    @Override
    public Page<@NonNull IconResponseDTO> listIcons(IconFilterRequestDTO filters) {
        Pageable pageable = PageRequest.of(filters.pageNumber(), ApiUtils.ELEMENTS_PER_PAGE);

        return iconRepository.findAll(specificationIconFilter.getByFilters(filters), pageable).map(iconMapper::toDTO);
    }
}
