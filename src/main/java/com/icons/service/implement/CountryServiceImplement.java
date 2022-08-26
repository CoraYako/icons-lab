package com.icons.service.implement;

import com.icons.dto.CountryDTO;
import com.icons.dto.filters.CountryFiltersDTO;
import com.icons.entity.ContinentEntity;
import com.icons.entity.CountryEntity;
import com.icons.entity.IconEntity;
import com.icons.mapper.CountryMapper;
import com.icons.repository.CountryRepository;
import com.icons.repository.spec.CountrySpecification;
import com.icons.service.ContinentService;
import com.icons.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CountryServiceImplement implements CountryService {
    private final CountryRepository countryRepository;
    private final CountrySpecification countrySpecification;
    private final CountryMapper countryMapper;
    private final ContinentService continentService;

    @Autowired
    public CountryServiceImplement(CountryRepository countryRepository, CountrySpecification countrySpecification, CountryMapper countryMapper, ContinentService continentService) {
        this.countryRepository = countryRepository;
        this.countrySpecification = countrySpecification;
        this.countryMapper = countryMapper;
        this.continentService = continentService;
    }

    @Override
    public CountryDTO save(CountryDTO dto) {
        CountryEntity entity = countryMapper.DTO2Entity(dto, false);
        ContinentEntity continentFound = continentService.findByIdEntity(dto.getContinent().getId());

        if (continentFound == null) {
            throw new NoSuchElementException("Continent could not be found.");
        }

        entity.setContinent(continentFound);
        continentService.addCountry(continentFound.getId(), entity);
        CountryEntity entitySaved = countryRepository.save(entity);

        return countryMapper.entity2DTO(entitySaved, false);
    }

    @Override
    public CountryDTO update(String id, CountryDTO dto) {
        CountryEntity entityFound = findByIdEntity(id);

        if (entityFound == null) {
            throw new NoSuchElementException("Country could not be found or doesn't exist.");
        }

        CountryEntity entity = countryMapper.DTO2Entity(dto, true);

        if (entity.getImage() != null && !entity.getImage().trim().isEmpty()) {
            entityFound.setImage(entity.getImage());
        }
        if (entity.getDenomination() != null && !entity.getDenomination().trim().isEmpty()) {
            entityFound.setDenomination(entity.getDenomination());
        }
        if (entity.getPopulation() != null && entity.getPopulation().signum() != -1) {
            entityFound.setPopulation(entity.getPopulation());
        }
        if (entity.getArea() != null && entity.getArea() > 0) {
            entityFound.setArea(entity.getArea());
        }

        CountryEntity entityUpdated = countryRepository.save(entityFound);

        return countryMapper.entity2DTO(entityUpdated, true);
    }

    @Override
    public List<CountryDTO> getAll() {
        List<CountryEntity> entityList = countryRepository.findAll();
        return countryMapper.entityList2DTOList(entityList, true);
    }

    @Override
    public void delete(String id) {
        countryRepository.deleteById(id);
    }

    @Override
    public List<CountryDTO> getByFilters(String name, String continent, List<String> icons, String order) {
        CountryFiltersDTO filtersDTO = new CountryFiltersDTO(name, continent, icons, order);
        List<CountryEntity> entityList = countryRepository.findAll(countrySpecification.getByFilters(filtersDTO));
        return countryMapper.entityList2DTOList(entityList, true);
    }

    @Override
    public CountryDTO findByIdDTO(String id) {
        Optional<CountryEntity> response = countryRepository.findById(id);

        return response.map(countryEntity -> countryMapper.entity2DTO(countryEntity, false)).orElse(null);
    }

    @Override
    public CountryEntity findByIdEntity(String id) {
        Optional<CountryEntity> response = countryRepository.findById(id);

        return response.orElse(null);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CountryEntity addContinent(String id, String idContinent) {
        ContinentEntity continentEntity = continentService.findByIdEntity(idContinent);
        CountryEntity entity = findByIdEntity(id);

        entity.setContinent(continentEntity);

        return countryRepository.save(entity);
    }

    @Override
    public CountryEntity addIcon(CountryEntity entity, IconEntity iconEntity) {
        CountryEntity entityFound = findByIdEntity(entity.getId());

        entityFound.getIcons().add(iconEntity);

        return countryRepository.save(entityFound);
    }
}
