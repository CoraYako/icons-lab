package com.icons.icon.model.mapper;

import com.icons.country.model.mapper.CountryMapper;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconBasicResponseDTO;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import com.icons.util.ApiUtils;
import com.icons.util.DataListResponseDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Component
@Validated
public class IconMapperImpl implements IconMapper {
    private final CountryMapper countryMapper;

    @Lazy
    public IconMapperImpl(CountryMapper countryMapper) {
        this.countryMapper = countryMapper;
    }

    @Override
    public IconEntity toEntity(IconRequestDTO dto) {
        IconEntity entity = new IconEntity();
        entity.setImage(dto.image());
        entity.setName(dto.name());
        entity.setCreationDate(LocalDate.parse(dto.creationDate(), ApiUtils.OF_PATTERN));
        entity.setHeight(dto.height());
        entity.setHistoryDescription(dto.historyDescription());
        return entity;
    }

    @Override
    public IconResponseDTO toCompleteDTO(IconEntity entity) {
        return new IconResponseDTO(
                entity.getId(),
                entity.getImage(),
                entity.getName(),
                entity.getCreationDate().format(ApiUtils.OF_PATTERN),
                entity.getHeight(),
                entity.getHistoryDescription(),
                new DataListResponseDTO(entity.getCountries().stream().map(countryMapper::toBasicInfoDTO).toList())
        );
    }

    @Override
    public IconBasicResponseDTO toBasicDTO(IconEntity entity) {
        return new IconBasicResponseDTO(
                entity.getId(),
                entity.getImage(),
                entity.getName(),
                entity.getCreationDate().format(ApiUtils.OF_PATTERN),
                entity.getHeight(),
                entity.getHistoryDescription()
        );
    }
}
