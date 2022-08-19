package com.icons.service.implement;

import com.icons.dto.IconDTO;
import com.icons.entity.IconEntity;
import com.icons.mapper.IconMapper;
import com.icons.repository.IconRepository;
import com.icons.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IconServiceImplement implements IconService {

    @Autowired
    private IconRepository iconRepository;

    @Autowired
    private IconMapper iconMapper;

    @Override
    @Transactional
    public IconDTO save(IconDTO dto) {
        IconEntity entity = iconMapper.DTO2Entity(dto,false);
        IconEntity entitySaved = iconRepository.save(entity);

        return iconMapper.entity2DTO(entitySaved, false);
    }

    @Override
    public List<IconDTO> getAll() {
        List<IconEntity> entityList = iconRepository.findAll();

        return iconMapper.entityList2DTOList(entityList, true);
    }

    @Override
    @Transactional
    public void delete(String id) {
        iconRepository.deleteById(id);
    }
}
