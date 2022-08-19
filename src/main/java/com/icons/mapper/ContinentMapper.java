package com.icons.mapper;

import com.icons.dto.ContinentDTO;
import com.icons.entity.ContinentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContinentMapper {

    private CountryMapper countryMapper;

    @Autowired
    @Lazy
    public void setCountryMapper(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    public CountryMapper getCountryMapper() {
        return countryMapper;
    }

    public ContinentEntity DTO2Entity(ContinentDTO dto) {
        ContinentEntity entity = new ContinentEntity();

        entity.setImage(dto.getImage());
        entity.setDenomination(dto.getDenomination());

        if (dto.getCountries() != null || !dto.getCountries().isEmpty()) {
            entity.setCountries(
                    getCountryMapper().DTOList2EntityList(dto.getCountries(), false)
            );
        }

        return entity;
    }

    public ContinentDTO entity2DTO(ContinentEntity entity) {
        ContinentDTO dto = new ContinentDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setDenomination(entity.getDenomination());

        if (entity.getCountries() != null || !entity.getCountries().isEmpty()) {
            dto.setCountries(
                    countryMapper.entityList2DTOList(entity.getCountries(), false)
            );
        }

        return dto;
    }

    public ContinentEntity DTOBasic2EntityBasic(ContinentDTO dto) {
        ContinentEntity entity = new ContinentEntity();

        entity.setImage(dto.getImage());
        entity.setDenomination(dto.getDenomination());

        return entity;
    }

    public ContinentDTO entityBasic2DTOBasic(ContinentEntity entity) {
        ContinentDTO dto = new ContinentDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setDenomination(entity.getDenomination());

        return dto;
    }

    public List<ContinentDTO> entityList2DTOList(List<ContinentEntity> entityList) {
        List<ContinentDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity -> dtoList.add(entity2DTO(entity)));

        return dtoList;
    }

    public List<ContinentEntity> DTOList2EntityList(List<ContinentDTO> dtoList) {
        List<ContinentEntity> entityList = new ArrayList<>();

        dtoList.forEach(dto -> entityList.add(DTO2Entity(dto)));

        return entityList;
    }
}
