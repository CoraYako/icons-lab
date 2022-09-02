package com.icons.service;

import com.icons.dto.ContinentDTO;
import com.icons.entity.ContinentEntity;
import com.icons.entity.CountryEntity;

import java.util.List;

public interface ContinentService {

    ContinentDTO save(ContinentDTO dto);

    List<ContinentDTO> getAll();

    void addCountry(String id, CountryEntity countryEntity);

    ContinentEntity getEntityById(String id);

    ContinentEntity getEntityByName(String name);
}
