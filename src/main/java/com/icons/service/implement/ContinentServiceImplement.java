package com.icons.service.implement;

import com.icons.dto.ContinentDTO;
import com.icons.entity.ContinentEntity;
import com.icons.entity.CountryEntity;
import com.icons.exception.ParamNotFoundException;
import com.icons.mapper.ContinentMapper;
import com.icons.repository.ContinentRepository;
import com.icons.service.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class ContinentServiceImplement implements ContinentService {

    private final ContinentMapper continentMapper;
    private final ContinentRepository continentRepository;

    @Autowired
    public ContinentServiceImplement(ContinentMapper continentMapper, ContinentRepository continentRepository) {
        this.continentMapper = continentMapper;
        this.continentRepository = continentRepository;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public ContinentDTO save(ContinentDTO dto) {
        ContinentEntity repositoryResponse = getEntityByName(dto.getDenomination());

        if (repositoryResponse != null && dto.getDenomination().equalsIgnoreCase(repositoryResponse.getDenomination())) {
            throw new EntityExistsException("The continent already exist.");
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
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void addCountry(String id, CountryEntity countryEntity) {
        ContinentEntity entity = getEntityById(id);
        entity.getCountries().add(countryEntity);
        continentRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ContinentEntity getEntityById(String id) {
        Optional<ContinentEntity> response = continentRepository.findById(id);
        if (response.isEmpty()) {
            throw new ParamNotFoundException("The continent can't be found or doesn't exist.");
        }
        return response.get();
    }

    @Override
    @Transactional(readOnly = true)
    public ContinentEntity getEntityByName(String name) {
        Optional<ContinentEntity> response = continentRepository.findByName(name);
        return response.orElse(null);
    }
}
