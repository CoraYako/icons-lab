package com.icons.continent.model.mapper;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.country.model.CountryEntity;
import com.icons.util.DataListResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
        return new ContinentResponseDTO(
                entity.getId(),
                entity.getImageURL(),
                entity.getName(),
                new DataListResponseDTO(
                        entity.getCountries().stream().map(CountryEntity::getName).toList()
                )
        );
    }
}
