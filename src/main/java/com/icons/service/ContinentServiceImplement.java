package com.icons.service;

import com.icons.dto.ContinentDTO;
import com.icons.entity.ContinentEntity;
import com.icons.mapper.ContinentMapper;
import com.icons.repository.ContinentRepository;
import com.icons.service.implement.ContinentService;
import com.icons.service.implement.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContinentServiceImplement implements ContinentService {

    @Autowired
    private ContinentMapper continentMapper;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ContinentRepository continentRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ContinentDTO save(ContinentDTO dto) {
        if (dto.getCountries() == null || dto.getCountries().isEmpty()) {
            return continentMapper.EntityBasic2DTOBasic(continentRepository.save(continentMapper.DTOBasic2EntityBasic(dto)));
        }

        /*List<CountryDTO> withId = new ArrayList<>();
        List<CountryDTO> withNoId = new ArrayList<>();

        if (!dto.getCountries().isEmpty() || dto.getCountries() != null) {
            dto.getCountries().forEach(aux -> {

                if (!aux.getId().isEmpty() || aux.getId() != null) {
                    var dtoFound = countryService.findById(aux.getId());
                    withId.add(dtoFound);
                }

                countryService.save(aux);
                withNoId.add(aux);
            });
        }*/

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
}
