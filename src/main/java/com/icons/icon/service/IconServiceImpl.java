package com.icons.icon.service;

import com.icons.country.model.CountryEntity;
import com.icons.country.service.CountryService;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import com.icons.icon.model.dto.IconUpdateRequestDTO;
import com.icons.icon.model.mapper.IconMapper;
import com.icons.icon.repository.IconRepository;
import com.icons.util.ApiUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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

    public IconServiceImpl(IconRepository iconRepository, IconMapper iconMapper, CountryService countryService) {
        this.iconRepository = iconRepository;
        this.iconMapper = iconMapper;
        this.countryService = countryService;
    }

    @Override
    public void createIcon(IconRequestDTO dto) {
        if (Objects.isNull(dto))
            throw new NullPointerException("The provided Geographical Icon is invalid or null.");
        if (Objects.isNull(dto.name()) || dto.name().trim().isEmpty())
            throw new NullPointerException("The provided Icon name is invalid or null.");
        if (iconRepository.existsByName(dto.name()))
            throw new EntityExistsException(String.format("An Icon is already created for name %s.", dto.name()));
        IconEntity icon = iconMapper.toEntity(dto);
        CountryEntity country = countryService.getCountryById(dto.countryId());
        icon.getCountries().add(country);
        iconRepository.save(icon);
    }

    @Override
    public IconResponseDTO updateIcon(String id, IconUpdateRequestDTO dto) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        if (Objects.isNull(dto))
            throw new NullPointerException("The provided Icon is invalid or null.");
        IconEntity icon = iconRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Icon not found for ID %s", id)));

        icon.setImage(dto.image());
        icon.setName(dto.name());
        icon.setHeight(dto.height());
        icon.setHistoryDescription(dto.historyDescription());
        if (!Objects.isNull(dto.creationDate()) && !dto.creationDate().trim().isEmpty())
            icon.setCreationDate(LocalDate.parse(dto.creationDate(), ApiUtils.OF_PATTERN));
        if (!Objects.isNull(dto.countriesId()) && !dto.countriesId().isEmpty()) {
            List<String> countriesId = icon.getCountries().stream().map(CountryEntity::getId).toList();
            List<String> filteredCountriesId = dto.countriesId()
                    .stream().filter(countryId -> !countriesId.contains(countryId)).toList();
            icon.getCountries().addAll(
                    filteredCountriesId
                            .stream()
                            .map(countryService::getCountryById)
                            .toList()
            );
        }

        return iconMapper.toCompleteDTO(iconRepository.save(icon));
    }

    @Override
    public IconEntity updateIcon(String id, CountryEntity country) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        IconEntity icon = getIconById(id);
        icon.getCountries().add(country);
        return iconRepository.save(icon);
    }

    @Override
    public IconResponseDTO getIconDTOById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        return iconRepository.findById(id)
                .map(iconMapper::toCompleteDTO)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Icon not found for ID %s", id)));
    }

    @Override
    public IconEntity getIconById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        return iconRepository.findById(id)
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
    public Page<IconResponseDTO> listIcons(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, ApiUtils.ELEMENTS_PER_PAGE);
        pageable.next().getPageNumber();
        return iconRepository.findAll(pageable).map(iconMapper::toCompleteDTO);
    }
}
