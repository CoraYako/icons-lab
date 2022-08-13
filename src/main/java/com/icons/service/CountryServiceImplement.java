package com.icons.service;

import com.icons.dto.CountryDTO;
import com.icons.entity.CountryEntity;
import com.icons.mapper.CountryMapper;
import com.icons.repository.CountryRepository;
import com.icons.service.implement.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImplement implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    private CountryMapper countryMapper;

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
    public CountryDTO findById(String id) {
        Optional<CountryEntity> response = countryRepository.findById(id);

        return response.map(countryEntity -> countryMapper.entity2DTO(countryEntity, false)).orElse(null);
    }
}
