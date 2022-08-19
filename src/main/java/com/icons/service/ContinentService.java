package com.icons.service;

import com.icons.dto.ContinentDTO;
import com.icons.dto.CountryDTO;
import com.icons.entity.ContinentEntity;
import com.icons.entity.CountryEntity;

import java.util.List;

public interface ContinentService {

    ContinentDTO save(ContinentDTO dto) throws Exception;

    List<ContinentDTO> getAll();

    void addCountry(String id, CountryEntity countryEntity);

    ContinentDTO addCountryList(ContinentDTO dto, List<CountryDTO> countryDTOList);

    ContinentDTO findByIdDTO(String id);

    ContinentEntity findByIdEntity(String id);

    ContinentEntity findByName(ContinentDTO dto);
}
