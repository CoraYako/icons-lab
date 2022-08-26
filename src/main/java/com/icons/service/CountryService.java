package com.icons.service;

import com.icons.dto.CountryDTO;
import com.icons.entity.CountryEntity;
import com.icons.entity.IconEntity;

import java.util.List;

public interface CountryService {

    CountryDTO save(CountryDTO dto);

    CountryDTO update(String id, CountryDTO dto);

    List<CountryDTO> getAll();

    void delete(String id);

    List<CountryDTO> getByFilters(String name, String continent, List<String> icons, String order);

    CountryDTO findByIdDTO(String id);

    CountryEntity findByIdEntity(String id);

    CountryEntity addContinent(String id, String idContinent);

    CountryEntity addIcon(CountryEntity entity, IconEntity iconEntity);
}
