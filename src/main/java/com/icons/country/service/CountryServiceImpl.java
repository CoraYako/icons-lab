package com.icons.country.service;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.service.ContinentService;
import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryFilterRequestDTO;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import com.icons.country.model.dto.CountryUpdateRequestDTO;
import com.icons.country.model.mapper.CountryMapper;
import com.icons.country.repository.CountryRepository;
import com.icons.country.repository.SpecificationCountryFilter;
import com.icons.util.ApiUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
@Validated
public class CountryServiceImpl implements CountryService {
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;
    private final ContinentService continentService;
    private final SpecificationCountryFilter specificationCountryFilter;

    public CountryServiceImpl(CountryMapper countryMapper, CountryRepository countryRepository,
                              ContinentService continentService, SpecificationCountryFilter specificationCountryFilter) {
        this.countryMapper = countryMapper;
        this.countryRepository = countryRepository;
        this.continentService = continentService;
        this.specificationCountryFilter = specificationCountryFilter;
    }

    @Override
    public void createCountry(CountryRequestDTO dto) {
        if (Objects.isNull(dto))
            throw new IllegalArgumentException("The provided Country is null or invalid.");
        if (Objects.isNull(dto.name()) || dto.name().trim().isEmpty())
            throw new IllegalArgumentException("The provided Country name is invalid.");
        if (countryRepository.existsByName(dto.name()))
            throw new EntityExistsException("A Country with this name already exists.");

        CountryEntity country = countryMapper.toEntity(dto);
        ContinentEntity continent = continentService.getById(dto.continentId());
        country.setContinent(continent);
        countryRepository.save(country);
    }

    @Override
    public CountryResponseDTO updateCountry(String id, CountryUpdateRequestDTO dto) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");
        if (Objects.isNull(dto))
            throw new IllegalArgumentException("The current Country is invalid or null.");
        if (countryRepository.existsByName(dto.name()))
            throw new IllegalArgumentException("A Country with this name already exists.");

        CountryEntity country = getById(id);

        country.setImageURL(dto.imageURL());
        country.setName(dto.name());
        country.setPopulation(dto.population());
        country.setArea(dto.area());

        if (!Objects.isNull(dto.continentId()) && !dto.continentId().trim().isEmpty())
            country.setContinent(continentService.getById(dto.continentId()));

        return countryMapper.toDTO(countryRepository.save(country));
    }

    @Override
    public Page<@NonNull CountryResponseDTO> listCountries(CountryFilterRequestDTO filters) {
        Pageable pageable = PageRequest.of(filters.pageNumber(), ApiUtils.ELEMENTS_PER_PAGE);

        return countryRepository.findAll(specificationCountryFilter.getByFilters(filters), pageable).map(countryMapper::toDTO);
    }

    @Override
    public void deleteCountry(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");

        CountryEntity country = countryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Country not found for ID: %s", id))
        );

        countryRepository.delete(country);
    }

    @Override
    public CountryResponseDTO getCountryById(String id) {
        return countryMapper.toDTO(getById(id));
    }

    @Override
    public CountryEntity getById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new IllegalArgumentException("The provided ID value doesn't represents a valid ID.");

        return countryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Country not found for ID: %s", id))
        );
    }
}
