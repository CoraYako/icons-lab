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
    public CountryResponseDTO createCountry(CountryRequestDTO dto) {
        if (Objects.isNull(dto))
            throw new NullRequestBodyException("Country");
        if (Objects.isNull(dto.name()) || dto.name().trim().isEmpty())
            throw new InvalidFieldException("name", "Must provide a country name");
        if (countryRepository.existsByName(dto.name()))
            throw new DuplicatedResourceException("Country", dto.name());

        CountryEntity country = countryMapper.toEntity(dto);
        ContinentEntity continent = continentService.getById(dto.continentId());
        country.setContinent(continent);
        country = countryRepository.save(country);

        return countryMapper.toDTO(country);
    }

    @Override
    public CountryResponseDTO updateCountry(String id, CountryUpdateRequestDTO dto) {
        if (ApiUtils.isNotValidUUID(id))
            throw new InvalidUUIDException(id);
        if (Objects.isNull(dto))
            throw new NullRequestBodyException("Country");
        if (countryRepository.existsByName(dto.name()))
            throw new DuplicatedResourceException("Country", dto.name());

        CountryEntity country = getById(id);

        var countryUpdated = applyUpdates(country, dto);

        return countryMapper.toDTO(countryRepository.save(countryUpdated));
    }

    @Override
    public Page<@NonNull CountryResponseDTO> listCountries(CountryFilterRequestDTO filters) {
        Pageable pageable = PageRequest.of(filters.pageNumber(), ApiUtils.ELEMENTS_PER_PAGE);

        return countryRepository.findAll(specificationCountryFilter.getByFilters(filters), pageable).map(countryMapper::toDTO);
    }

    @Override
    public void deleteCountry(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new InvalidUUIDException(id);

        CountryEntity country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country", id));

        countryRepository.delete(country);
    }

    @Override
    public CountryResponseDTO getCountryById(String id) {
        return countryMapper.toDTO(getById(id));
    }

    @Override
    public CountryEntity getById(String id) {
        if (ApiUtils.isNotValidUUID(id))
            throw new InvalidUUIDException(id);

        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country", id));
    }

    private CountryEntity applyUpdates(CountryEntity country, CountryUpdateRequestDTO dto) {
        if (Objects.nonNull(dto.imageURL()) && !dto.imageURL().trim().isEmpty())
            country.setImageURL(dto.imageURL());

        if (Objects.nonNull(dto.name()) && !dto.name().trim().isEmpty())
            country.setName(dto.name());

        if (dto.population() > 1)
            country.setPopulation(dto.population());

        if (dto.area() > 1)
            country.setArea(dto.area());

        if (Objects.nonNull(dto.continentId()) && !dto.continentId().trim().isEmpty())
            country.setContinent(continentService.getById(dto.continentId()));

        return country;
    }
}
