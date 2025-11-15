package com.icons.continent.model.mapper;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.country.model.CountryEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Validated
public class ContinentMapperImpl implements ContinentMapper {

    public ContinentMapperImpl() {
    }

    @Override
    public ContinentEntity toEntity(ContinentRequestDTO dto) {
        ContinentEntity entity = new ContinentEntity();
        entity.setImageURL(dto.imageURL());
        entity.setName(dto.name());
        return entity;
    }

    @Override
    public ContinentResponseDTO toDTO(ContinentEntity entity) {
        Set<String> countries = CollectionUtils.isEmpty(entity.getCountries()) ?
                Collections.emptySet() :
                entity.getCountries().stream().map(CountryEntity::getName).collect(Collectors.toSet());

        return new ContinentResponseDTO(
                entity.getId(),
                entity.getImageURL(),
                entity.getName(),
                countries
        );
    }
}
