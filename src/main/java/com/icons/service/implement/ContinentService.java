package com.icons.service.implement;

import com.icons.dto.ContinentDTO;

import java.util.List;

public interface ContinentService {

    ContinentDTO save(ContinentDTO dto);

    List<ContinentDTO> getAll();
}
