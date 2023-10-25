package com.icons.country.service;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.service.ContinentService;
import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import com.icons.country.model.dto.CountryUpdateRequestDTO;
import com.icons.country.model.mapper.CountryMapper;
import com.icons.country.repository.CountryRepository;
import com.icons.icon.model.IconEntity;
import com.icons.icon.service.IconService;
import com.icons.util.ApiUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Service
@Validated
public class CountryServiceImpl implements CountryService {
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;
    private final ContinentService continentService;
    private final IconService iconService;

    public CountryServiceImpl(CountryMapper countryMapper,
                              CountryRepository countryRepository,
                              ContinentService continentService,
                              @Lazy IconService iconService) {
        this.countryMapper = countryMapper;
        this.countryRepository = countryRepository;
        this.continentService = continentService;
        this.iconService = iconService;
    }

    @Override
    public void createCountry(CountryRequestDTO dto) {
        if (Objects.isNull(dto))
            throw new NullPointerException("The provided Country is null or invalid.");
        if (Objects.isNull(dto.name()) || dto.name().trim().isEmpty())
            throw new NullPointerException("The provided Country name is invalid.");
        if (countryRepository.existsByName(dto.name()))
            throw new EntityExistsException("Trying to create an existent Country.");
        CountryEntity country = countryMapper.toEntity(dto);
        ContinentEntity continent = continentService.getContinentById(dto.continentId());
        country.setContinent(continent);
        countryRepository.save(country);
    }

    @Override
    public CountryResponseDTO updateCountry(String id, CountryUpdateRequestDTO dto) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        if (Objects.isNull(dto))
            throw new NullPointerException("The current Country is invalid or null.");
        if (countryRepository.existsByName(dto.name()))
            throw new IllegalArgumentException(
                    String.format("There is already a Country in the database with the provided name %s", dto.name()));
        CountryEntity country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Country not found for ID: %s", id)));

        country.setImage(dto.image());
        country.setName(dto.name());
        country.setPopulation(dto.population());
        country.setArea(dto.area());
        if (!Objects.isNull(dto.continentId()) && !dto.continentId().trim().isEmpty())
            country.setContinent(continentService.getContinentById(dto.continentId()));
        if (!Objects.isNull(dto.iconsId()) && !dto.iconsId().isEmpty()) {
            List<String> iconsId = country.getIcons().stream().map(IconEntity::getId).toList();
            dto.iconsId().stream()
                    .filter(iconId -> !iconsId.contains(iconId))
                    .toList()
                    .forEach(iconId -> country.getIcons().add(iconService.updateIcon(iconId, country)));
        }
        return countryMapper.toCompleteDTO(countryRepository.save(country));
    }

    @Override
    public Page<CountryResponseDTO> listCountries(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, ApiUtils.ELEMENTS_PER_PAGE);
        pageable.next().getPageNumber();
        return countryRepository.findAll(pageable).map(countryMapper::toCompleteDTO);
    }

    @Override
    public void deleteCountry(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        CountryEntity country = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Country not found for ID: %s", id)));
        countryRepository.delete(country);
    }

    @Override
    public CountryResponseDTO getCountryDTOById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        return countryRepository.findById(id).map(countryMapper::toCompleteDTO)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Country not found for ID: %s", id)));
    }

    @Override
    public CountryEntity getCountryById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Country not found for name: %s", id)));
    }
}
