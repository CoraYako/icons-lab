package com.icons.continent.model.mapper;

import com.icons.continent.model.ContinentEntity;
import com.icons.continent.model.dto.ContinentBasicResponseDTO;
import com.icons.continent.model.dto.ContinentRequestDTO;
import com.icons.continent.model.dto.ContinentResponseDTO;
import com.icons.country.model.mapper.CountryMapper;
import com.icons.util.DataListResponseDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class ContinentMapperImpl implements ContinentMapper {
    private final CountryMapper countryMapper;

    @Lazy
    public ContinentMapperImpl(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    @Override
    public ContinentEntity toEntity(ContinentRequestDTO dto) {
        ContinentEntity entity = new ContinentEntity();
        entity.setImage(dto.image());
        entity.setName(dto.name());
        return entity;
    }

    @Override
    public ContinentBasicResponseDTO toBasicDTO(ContinentEntity entity) {
        return new ContinentBasicResponseDTO(
                entity.getId(),
                entity.getImage(),
                entity.getName()
        );
    }

    @Override
    public ContinentResponseDTO toCompleteDTO(ContinentEntity entity) {
        return new ContinentResponseDTO(
                entity.getId(),
                entity.getImage(),
                entity.getName(),
                new DataListResponseDTO(
                        entity.getCountries().stream().map(countryMapper::toBasicDTO).toList()
                )
        );
    }
}
