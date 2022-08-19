package com.icons.service.implement;

import com.icons.dto.CountryDTO;
import com.icons.entity.ContinentEntity;
import com.icons.entity.CountryEntity;
import com.icons.mapper.CountryMapper;
import com.icons.repository.CountryRepository;
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

    @Autowired
    private CountryRepository countryRepository;
    private CountryMapper countryMapper;
    private ContinentService continentService;

    @Autowired
    public void setContinentService(ContinentService continentService) {
        this.continentService = continentService;
    }

    public ContinentService getContinentService() {
        return continentService;
    }

    @Autowired
    public void setCountryMapper(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    public CountryMapper getCountryMapper() {
        return countryMapper;
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
    public List<CountryDTO> getAll() {
        List<CountryEntity> entityList = countryRepository.findAll();
        return countryMapper.entityList2DTOList(entityList, true);
    }

    @Override
    public void delete(String id) {
        countryRepository.deleteById(id);
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
}
