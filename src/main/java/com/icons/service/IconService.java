package com.icons.service;

import com.icons.dto.IconDTO;

import java.util.List;

public interface IconService {

    IconDTO save(IconDTO dto);

    List<IconDTO> getAll();

    void delete(String id);
}
