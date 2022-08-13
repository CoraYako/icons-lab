package com.icons.mapper;

import com.icons.dto.CountryDTO;
import com.icons.dto.IconDTO;
import com.icons.entity.CountryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryMapper {

    private IconMapper iconMapper;

    private ContinentMapper continentMapper;

    @Autowired
    public void setContinentMapper(ContinentMapper continentMapper) {
        this.continentMapper = continentMapper;
    }

    public ContinentMapper getContinentMapper() {
        return continentMapper;
    }

    @Autowired
    public void setIconMapper(IconMapper iconMapper) {
        this.iconMapper = iconMapper;
    }

    public IconMapper getIconMapper() {
        return iconMapper;
    }

    public CountryDTO entity2DTO(CountryEntity entity, boolean loadIcons) {
        CountryDTO dto = new CountryDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setDenomination(entity.getDenomination());
        dto.setPopulation(entity.getPopulation());
        dto.setArea(entity.getArea());
        /*dto.setContinentId(entity.getContinentId());*/

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
        /*entity.setContinentId(dto.getContinentId());*/

        if (loadIcons) {
            entity.setIcons(
                    iconMapper.DTOList2EntityList(dto.getIcons(), false)
            );
        }

        return entity;
    }

    public List<CountryDTO> entityList2DTOList(List<CountryEntity> entityList, boolean loadIcons) {
        List<CountryDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity -> dtoList.add(entity2DTO(entity, loadIcons)));

        return dtoList;
    }

    public List<CountryEntity> DTOList2EntityList(List<CountryDTO> dtoList, boolean loadIcons) {
        List<CountryEntity> entityList = new ArrayList<>();

        dtoList.forEach(dto -> entityList.add(DTO2Entity(dto, loadIcons)));

        return entityList;
    }
}
