package com.icons.country.model.mapper;

import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import com.icons.icon.model.IconEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<String> icons = CollectionUtils.isEmpty(entity.getIcons()) ?
                Collections.emptySet() :
                entity.getIcons().stream().map(IconEntity::getName).collect(Collectors.toSet());

        return new CountryResponseDTO(
                entity.getId(),
                entity.getImageURL(),
                entity.getName(),
                entity.getPopulation(),
                entity.getArea(),
                entity.getContinent().getName(),
                icons
        );
    }
}
