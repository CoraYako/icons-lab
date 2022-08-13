package com.icons.service.implement;

import com.icons.dto.CountryDTO;

import java.util.List;

public interface CountryService {

    CountryDTO save(CountryDTO dto);

    List<CountryDTO> getAll();

    void delete(String id);

    CountryDTO findById(String id);
}
