package com.icons.service.implement;

import com.icons.dto.ContinentDTO;
import com.icons.dto.CountryDTO;
import com.icons.entity.ContinentEntity;
import com.icons.entity.CountryEntity;
import com.icons.mapper.ContinentMapper;
import com.icons.repository.ContinentRepository;
import com.icons.service.ContinentService;
import com.icons.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContinentServiceImplement implements ContinentService {

    private CountryService countryService;

    @Autowired
    private ContinentMapper continentMapper;

    @Autowired
    @Lazy
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public CountryService getCountryService() {
        return countryService;
    }

    @Autowired
    private ContinentRepository continentRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ContinentDTO save(ContinentDTO dto) throws Exception {
        ContinentEntity repositoryResponse = findByName(dto);

        if (repositoryResponse != null && dto.getDenomination().equalsIgnoreCase(repositoryResponse.getDenomination())) {
            throw new Exception("The continent already exist.");
        }

        ContinentEntity entity = continentMapper.DTO2Entity(dto);
        ContinentEntity entitySaved = continentRepository.save(entity);

        return continentMapper.entity2DTO(entitySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContinentDTO> getAll() {
        List<ContinentEntity> entityList = continentRepository.findAll();

        return continentMapper.entityList2DTOList(entityList);
    }

    @Override
    public void addCountry(String id, CountryEntity countryEntity) {
        ContinentEntity entity = findByIdEntity(id);
        entity.getCountries().add(countryEntity);

        continentRepository.save(entity);
    }

    @Override
    public ContinentDTO addCountryList(ContinentDTO dto, List<CountryDTO> countryDTOList) {


        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public ContinentDTO findByIdDTO(String id) {
        Optional<ContinentEntity> response = continentRepository.findById(id);

        return response.map(entity -> continentMapper.entity2DTO(entity)).orElse(null);
    }

    @Override
    public ContinentEntity findByIdEntity(String id) {
        Optional<ContinentEntity> response = continentRepository.findById(id);

        return response.orElse(null);
    }

    @Override
    public ContinentEntity findByName(ContinentDTO dto) {
        Optional<ContinentEntity> request = continentRepository.findByName(dto.getDenomination());

        return request.orElse(null);
    }


}
