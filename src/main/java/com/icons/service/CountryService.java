package com.icons.service;

import com.icons.dto.CountryDTO;
import com.icons.entity.CountryEntity;

import java.util.List;

public interface CountryService {

    CountryDTO save(CountryDTO dto);

    List<CountryDTO> getAll();

    void delete(String id);

    CountryDTO findByIdDTO(String id);

    CountryEntity findByIdEntity(String id);

    CountryEntity addContinent(String id, String idContinent);
}
