package com.icons.icon.model.mapper;

import com.icons.country.model.CountryEntity;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconRequestDTO;
import com.icons.icon.model.dto.IconResponseDTO;
import com.icons.util.ApiUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Validated
public class IconMapperImpl implements IconMapper {

    public IconMapperImpl() {
    }

    @Override
    public IconEntity toEntity(IconRequestDTO dto) {
        IconEntity entity = new IconEntity();
        entity.setImageURL(dto.imageURL());
        entity.setName(dto.name());
        entity.setCreationDate(LocalDate.parse(dto.creationDate(), ApiUtils.OF_PATTERN));
        entity.setHeight(dto.height());
        entity.setHistoryDescription(dto.historyDescription());
        return entity;
    }

    @Override
    public IconResponseDTO toDTO(IconEntity entity) {
        Set<String> countries = CollectionUtils.isEmpty(entity.getCountries()) ?
                Collections.emptySet() :
                entity.getCountries().stream().map(CountryEntity::getName).collect(Collectors.toSet());

        return new IconResponseDTO(
                entity.getId(),
                entity.getImageURL(),
                entity.getName(),
                entity.getCreationDate().format(ApiUtils.OF_PATTERN),
                entity.getHeight(),
                entity.getHistoryDescription(),
                countries
        );
    }
}
