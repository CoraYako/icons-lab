package com.icons.service;

import com.icons.dto.IconDTO;
import com.icons.entity.IconEntity;

import java.util.List;

public interface IconService {

    IconDTO save(IconDTO dto);

    List<IconDTO> getAll();

    void delete(String id);

    IconDTO update(String id, IconDTO dto);

    IconEntity findById(String id);

    IconEntity findByName(String name);

    List<IconDTO> getByFilters(String name, String date, List<String> countries, String order);
}
