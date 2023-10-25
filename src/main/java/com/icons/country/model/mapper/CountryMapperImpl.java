package com.icons.country.model.mapper;

import com.icons.continent.model.mapper.ContinentMapper;
import com.icons.country.model.CountryEntity;
import com.icons.country.model.dto.CountryBasicInfoResponseDTO;
import com.icons.country.model.dto.CountryBasicResponseDTO;
import com.icons.country.model.dto.CountryRequestDTO;
import com.icons.country.model.dto.CountryResponseDTO;
import com.icons.icon.model.mapper.IconMapper;
import com.icons.util.DataListResponseDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class CountryMapperImpl implements CountryMapper {
    private final ContinentMapper continentMapper;
    private final IconMapper iconMapper;


    public CountryMapperImpl(ContinentMapper continentMapper, @Lazy IconMapper iconMapper) {
        this.continentMapper = continentMapper;
        this.iconMapper = iconMapper;
    }

    @Override
    public CountryEntity toEntity(CountryRequestDTO dto) {
        CountryEntity entity = new CountryEntity();
        entity.setImage(dto.image());
        entity.setName(dto.name());
        entity.setPopulation(dto.population());
        entity.setArea(dto.area());
        return entity;
    }

    @Override
    public CountryBasicResponseDTO toBasicDTO(CountryEntity entity) {
        return new CountryBasicResponseDTO(
                entity.getId(),
                entity.getImage(),
                entity.getName(),
                entity.getPopulation(),
                entity.getArea(),
                new DataListResponseDTO(
                        entity.getIcons().stream().map(iconMapper::toBasicDTO).toList()
                )
        );
    }

    @Override
    public CountryBasicInfoResponseDTO toBasicInfoDTO(CountryEntity entity) {
        return new CountryBasicInfoResponseDTO(
                entity.getId(),
                entity.getImage(),
                entity.getName(),
                entity.getPopulation(),
                entity.getArea(),
                continentMapper.toBasicDTO(entity.getContinent())
        );
    }

    @Override
    public CountryResponseDTO toCompleteDTO(CountryEntity entity) {
        return new CountryResponseDTO(
                entity.getId(),
                entity.getImage(),
                entity.getName(),
                entity.getPopulation(),
                entity.getArea(),
                continentMapper.toBasicDTO(entity.getContinent()),
                new DataListResponseDTO(entity.getIcons().stream().map(iconMapper::toBasicDTO).toList())
        );
    }
}
