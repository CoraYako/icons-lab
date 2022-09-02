package com.icons.service.implement;

import com.icons.dto.CountryDTO;
import com.icons.dto.filters.CountryFiltersDTO;
import com.icons.entity.ContinentEntity;
import com.icons.entity.CountryEntity;
import com.icons.entity.IconEntity;
import com.icons.exception.ParamNotFoundException;
import com.icons.mapper.CountryMapper;
import com.icons.repository.CountryRepository;
import com.icons.repository.spec.CountrySpecification;
import com.icons.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImplement implements CountryService {

    private final CountryRepository countryRepository;
    private final CountrySpecification countrySpecification;
    private final CountryMapper countryMapper;
    private final ContinentServiceImplement continentServiceImplement;

    @Autowired
    public CountryServiceImplement(CountryRepository countryRepository, CountrySpecification countrySpecification, CountryMapper countryMapper, ContinentServiceImplement continentServiceImplement) {
        this.countryRepository = countryRepository;
        this.countrySpecification = countrySpecification;
        this.countryMapper = countryMapper;
        this.continentServiceImplement = continentServiceImplement;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public CountryDTO save(CountryDTO dto) {
        CountryEntity repositoryResponse = getEntityByName(dto.getDenomination());

        if (repositoryResponse != null && dto.getDenomination().equalsIgnoreCase(repositoryResponse.getDenomination())) {
            throw new EntityExistsException("The country already exist.");
        }

        CountryEntity entity = countryMapper.DTO2Entity(dto, false);
        ContinentEntity continentFound = continentServiceImplement.getEntityById(dto.getContinent().getId());

        entity.setContinent(continentFound);
        continentServiceImplement.addCountry(continentFound.getId(), entity);
        CountryEntity entitySaved = countryRepository.save(entity);

        return countryMapper.entity2DTO(entitySaved, false);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public CountryDTO update(String id, CountryDTO dto) {
        CountryEntity entityFound = getEntityById(id);

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
        if (dto.getContinent() != null && !dto.getContinent().getId().trim().isEmpty()) {
            ContinentEntity continentEntity = continentServiceImplement.getEntityById(dto.getContinent().getId());
            entityFound.setContinent(continentEntity);
        }

        CountryEntity entityUpdated = countryRepository.save(entityFound);
        return countryMapper.entity2DTO(entityUpdated, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryDTO> getAll() {
        List<CountryEntity> entityList = countryRepository.findAll();
        return countryMapper.entityList2DTOList(entityList, true);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void delete(String id) {
        countryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryDTO> getByFilters(String name, String continent, List<String> icons, String order) {
        CountryFiltersDTO filtersDTO = new CountryFiltersDTO(name, continent, icons, order);
        List<CountryEntity> entityList = countryRepository.findAll(countrySpecification.getByFilters(filtersDTO));
        return countryMapper.entityList2DTOList(entityList, true);
    }

    @Override
    @Transactional(readOnly = true)
    public CountryEntity getEntityById(String id) {
        Optional<CountryEntity> response = countryRepository.findById(id);
        if (response.isEmpty()) {
            throw new ParamNotFoundException("The country can't be found or doesn't exist.");
        }
        return response.get();
    }

    @Override
    @Transactional(readOnly = true)
    public CountryEntity getEntityByName(String name) {
        Optional<CountryEntity> response = countryRepository.findByName(name);
        return response.orElse(null);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public CountryEntity addIcon(CountryEntity entity, IconEntity iconEntity) {
        CountryEntity entityFound = getEntityById(entity.getId());
        entityFound.getIcons().add(iconEntity);
        return countryRepository.save(entityFound);
    }
}
