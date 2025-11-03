package com.icons.country.model.mapper;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import com.icons.icon.model.IconEntity;
import com.icons.util.DataListResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class CountryMapperImpl implements CountryMapper {

    public CountryMapperImpl() {
    }

    @Override
    public CountryEntity toEntity(CountryRequestDTO dto) {
        CountryEntity entity = new CountryEntity();
        entity.setImageURL(dto.imageURL());
        entity.setName(dto.name());
        entity.setPopulation(dto.population());
        entity.setArea(dto.area());
        return entity;
    }

    @Override
    public CountryResponseDTO toDTO(CountryEntity entity) {
        return new CountryResponseDTO(
                entity.getId(),
                entity.getImageURL(),
                entity.getName(),
                entity.getPopulation(),
                entity.getArea(),
                entity.getContinent().getName(),
                new DataListResponseDTO(entity.getIcons().stream().map(IconEntity::getName).toList())
        );
    }
}
