package com.icons.mapper;

import com.icons.dto.CountryDTO;
import com.icons.dto.IconDTO;
import com.icons.entity.CountryEntity;
import com.icons.entity.IconEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryMapper {

    private final IconMapper iconMapper;
    private final ContinentMapper continentMapper;

    @Autowired
    public CountryMapper(@Lazy IconMapper iconMapper, ContinentMapper continentMapper) {
        this.iconMapper = iconMapper;
        this.continentMapper = continentMapper;
    }

    public CountryDTO entity2DTO(CountryEntity entity, boolean loadIcons) {
        CountryDTO dto = new CountryDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setDenomination(entity.getDenomination());
        dto.setPopulation(entity.getPopulation());
        dto.setArea(entity.getArea());
        dto.setContinent(continentMapper.entityBasic2DTOBasic(entity.getContinent()));

        if (loadIcons) {
            List<IconDTO> iconsDTOList = iconMapper.entityList2DTOList(entity.getIcons(), false);
            dto.setIcons(iconsDTOList);
        }

        return dto;
    }

    public CountryEntity DTO2Entity(CountryDTO dto, boolean loadIcons) {
        CountryEntity entity = new CountryEntity();

        entity.setImage(dto.getImage());
        entity.setDenomination(dto.getDenomination());
        entity.setPopulation(dto.getPopulation());
        entity.setArea(dto.getArea());

        if (dto.getContinent() != null) {
            entity.setContinent(continentMapper.DTO2Entity(dto.getContinent()));
        }
        if (loadIcons) {
            List<IconEntity> iconsEntityList = iconMapper.DTOList2EntityList(dto.getIcons(), false);
            entity.setIcons(iconsEntityList);
        }

        return entity;
    }

    public CountryDTO entityBasic2DTOBasic(CountryEntity entity) {
        CountryDTO dto = new CountryDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setDenomination(entity.getDenomination());
        dto.setPopulation(entity.getPopulation());
        dto.setArea(entity.getArea());
        dto.setContinent(continentMapper.entityBasic2DTOBasic(entity.getContinent()));

        return dto;
    }

    public List<CountryDTO> entityList2DTOList(List<CountryEntity> entityList, boolean loadIcons) {
        List<CountryDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity -> dtoList.add(entity2DTO(entity, loadIcons)));

        return dtoList;
    }

    public List<CountryDTO> entityList2DTOListBasic(List<CountryEntity> entityList) {
        List<CountryDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity -> dtoList.add(entityBasic2DTOBasic(entity)));

        return dtoList;
    }

    public List<CountryEntity> DTOList2EntityList(List<CountryDTO> dtoList, boolean loadIcons) {
        List<CountryEntity> entityList = new ArrayList<>();

        dtoList.forEach(dto -> entityList.add(DTO2Entity(dto, loadIcons)));

        return entityList;
    }
}
