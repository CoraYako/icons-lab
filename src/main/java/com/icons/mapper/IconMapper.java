package com.icons.mapper;

import com.icons.dto.CountryDTO;
import com.icons.dto.IconDTO;
import com.icons.entity.IconEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class IconMapper {

    private CountryMapper countryMapper;

    @Autowired
    @Lazy
    public void setCountryMapper(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    public CountryMapper getCountryMapper() {
        return countryMapper;
    }

    public IconDTO entity2DTO(IconEntity entity, boolean loadCountries) {
        IconDTO dto = new IconDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setDenomination(entity.getDenomination());
        dto.setCreation(entity.getCreation().toString());
        dto.setHeight(entity.getHeight());
        dto.setHistory(entity.getHistory());

        if (loadCountries) {
            List<CountryDTO> dtoList = countryMapper.entityList2DTOListBasic(entity.getCountries());
            dto.setCountries(dtoList);
        }

        return dto;
    }

    public IconEntity DTO2Entity(IconDTO dto, boolean loadCountries) {
        IconEntity entity = new IconEntity();

        entity.setImage(dto.getImage());
        entity.setDenomination(dto.getDenomination());
        entity.setCreation(
                string2LocalDate(dto.getCreation())
        );
        entity.setHeight(dto.getHeight());
        entity.setHistory(dto.getHistory());

        if (loadCountries) {
            entity.setCountries(
                    countryMapper.DTOList2EntityList(dto.getCountries(), false)
            );
        }

        return entity;
    }

    public List<IconDTO> entityList2DTOList(List<IconEntity> entityList, boolean loadCountries) {
        List<IconDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity -> dtoList.add(entity2DTO(entity, loadCountries)));

        return dtoList;
    }

    public List<IconEntity> DTOList2EntityList(List<IconDTO> dtoList, boolean loadCountries) {
        List<IconEntity> entityList = new ArrayList<>();

        dtoList.forEach(dto -> entityList.add(DTO2Entity(dto, loadCountries)));

        return entityList;
    }

    public LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        return LocalDate.parse(stringDate, formatter);
    }

    public String localDate2String(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
