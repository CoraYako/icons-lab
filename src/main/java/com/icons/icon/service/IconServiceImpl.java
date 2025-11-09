package com.icons.icon.service;

import com.icons.country.model.CountryEntity;
import com.icons.country.service.CountryService;
import com.icons.exception.*;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconFilterRequestDTO;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import com.icons.icon.model.dto.IconUpdateRequestDTO;
import com.icons.icon.model.mapper.IconMapper;
import com.icons.icon.repository.IconRepository;
import com.icons.icon.repository.SpecificationIconFilter;
import com.icons.util.ApiUtils;
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
            throw new NullRequestBodyException("Geographic Icon");
        if (Objects.isNull(dto.name()) || dto.name().trim().isEmpty())
            throw new InvalidFieldException("name", "Must provide an icon name");
        if (iconRepository.existsByName(dto.name()))
            throw new DuplicatedResourceException("Icon", dto.name());

        IconEntity icon = iconMapper.toEntity(dto);
        CountryEntity country = countryService.getById(dto.countryId());
        icon.getCountries().add(country);
        iconRepository.save(icon);
    }

    @Override
    public IconResponseDTO updateIcon(String id, IconUpdateRequestDTO dto) {
        if (ApiUtils.isNotValidUUID(id))
            throw new InvalidUUIDException(id);
        if (Objects.isNull(dto))
            throw new NullRequestBodyException("Geographic Icon");

        IconEntity icon = iconRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Icon", id));

        var iconUpdated = applyUpdates(icon, dto);

        return iconMapper.toDTO(iconRepository.save(iconUpdated));
    }

    @Override
    public IconResponseDTO getIconById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new InvalidUUIDException(id);
        return iconRepository.findById(id)
                .map(iconMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Icon", id));
    }

    @Override
    public void deleteIcon(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new InvalidUUIDException(id);
        IconEntity icon = iconRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Icon", id));
        iconRepository.delete(icon);
    }

    @Override
    public Page<@NonNull IconResponseDTO> listIcons(IconFilterRequestDTO filters) {
        Pageable pageable = PageRequest.of(filters.pageNumber(), ApiUtils.ELEMENTS_PER_PAGE);

        return iconRepository.findAll(specificationIconFilter.getByFilters(filters), pageable).map(iconMapper::toDTO);
    }

    private IconEntity applyUpdates(IconEntity iconEntity, IconUpdateRequestDTO dto) {
        if (Objects.nonNull(dto.imageURL()) && !dto.imageURL().trim().isEmpty())
            iconEntity.setImageURL(dto.imageURL());

        if (Objects.nonNull(dto.name()) && !dto.name().trim().isEmpty())
            iconEntity.setName(dto.name());

        if (dto.height() > 1)
            iconEntity.setHeight(dto.height());

        if (Objects.nonNull(dto.historyDescription()) && !dto.historyDescription().trim().isEmpty())
            iconEntity.setHistoryDescription(dto.historyDescription());

        if (!Objects.isNull(dto.creationDate()) && !dto.creationDate().trim().isEmpty())
            iconEntity.setCreationDate(LocalDate.parse(dto.creationDate(), ApiUtils.OF_PATTERN));

        if (!Objects.isNull(dto.countriesId()) && !dto.countriesId().isEmpty()) {
            List<String> countriesId = iconEntity.getCountries().stream().map(CountryEntity::getId).toList();
            List<String> filteredCountriesId = dto.countriesId()
                    .stream()
                    .filter(countryId -> !countriesId.contains(countryId))
                    .toList();
            iconEntity.getCountries().addAll(
                    filteredCountriesId
                            .stream()
                            .map(countryService::getById)
                            .toList()
            );
        }

        return iconEntity;
    }
}
