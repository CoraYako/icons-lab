package com.icons.service.implement;

import com.icons.dto.IconDTO;
import com.icons.entity.CountryEntity;
import com.icons.entity.IconEntity;
import com.icons.mapper.IconMapper;
import com.icons.repository.IconRepository;
import com.icons.service.CountryService;
import com.icons.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IconServiceImplement implements IconService {

    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private IconMapper iconMapper;
    @Autowired
    private CountryService countryService;

    @Override
    @Transactional
    public IconDTO save(IconDTO dto) {
        IconEntity entity = iconMapper.DTO2Entity(dto, false);

        IconEntity entitySaved = iconRepository.save(entity);

        List<CountryEntity> countryEntityList = new ArrayList<>();

        dto.getCountries().forEach(country -> {
            var countryFound = countryService.findByIdEntity(country.getId());
            if (countryFound != null) {
                var countryWithIcon = countryService.addIcon(countryFound, entitySaved);
                countryEntityList.add(countryWithIcon);

            }
        });

        entity.setCountries(countryEntityList);

        return iconMapper.entity2DTO(iconRepository.save(entity), true);
    }

    @Override
    public IconDTO update(String id, IconDTO dto) {
        IconEntity entityFound = findById(id);

        if (entityFound == null) {
            throw new NoSuchElementException("Icon could not be found or doesn't exist.");
        }

        if (dto.getCreation() == null) {
            dto.setCreation(iconMapper.localDate2String(entityFound.getCreation()));
        }

        IconEntity entity = iconMapper.DTO2Entity(dto, true);

        if (dto.getImage() != null && !dto.getImage().trim().isEmpty()) {
            entityFound.setImage(entity.getImage());
        }
        if (dto.getDenomination() != null && !dto.getDenomination().trim().isEmpty()) {
            entityFound.setDenomination(entity.getDenomination());
        }
        if (dto.getCreation() != null && !dto.getCreation().isEmpty()) {
            entityFound.setCreation(entity.getCreation());
        }
        if (dto.getHeight() != null) {
            entityFound.setHeight(entity.getHeight());
        }
        if (dto.getHistory() != null && !dto.getHistory().isEmpty()) {
            entityFound.setHistory(entity.getHistory());
        }
        /*entityFound.setCountries(entity.getCountries());*/

        return iconMapper.entity2DTO(iconRepository.save(entityFound), true);
    }

    @Override
    public IconEntity findById(String id) {
        Optional<IconEntity> response = iconRepository.findById(id);

        return response.orElse(null);
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
