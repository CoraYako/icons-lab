package com.icons.service.implement;

import com.icons.dto.IconDTO;
import com.icons.dto.filters.IconFiltersDTO;
import com.icons.entity.CountryEntity;
import com.icons.entity.IconEntity;
import com.icons.mapper.IconMapper;
import com.icons.repository.IconRepository;
import com.icons.repository.spec.IconSpecification;
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
    private final IconRepository iconRepository;
    private final IconSpecification iconSpecification;
    private final IconMapper iconMapper;
    private final CountryService countryService;

    @Autowired
    public IconServiceImplement(IconRepository iconRepository, IconSpecification iconSpecification, IconMapper iconMapper, CountryService countryService) {
        this.iconRepository = iconRepository;
        this.iconSpecification = iconSpecification;
        this.iconMapper = iconMapper;
        this.countryService = countryService;
    }

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

        if (entity.getImage() != null && !entity.getImage().trim().isEmpty()) {
            entityFound.setImage(entity.getImage());
        }
        if (entity.getDenomination() != null && !entity.getDenomination().trim().isEmpty()) {
            entityFound.setDenomination(entity.getDenomination());
        }
        if (entity.getCreation() != null) {
            entityFound.setCreation(entity.getCreation());
        }
        if (entity.getHeight() != null && entity.getHeight() > 0) {
            entityFound.setHeight(entity.getHeight());
        }
        if (entity.getHistory() != null && !entity.getHistory().trim().isEmpty()) {
            entityFound.setHistory(entity.getHistory());
        }

        return iconMapper.entity2DTO(iconRepository.save(entityFound), true);
    }

    @Override
    public IconEntity findById(String id) {
        Optional<IconEntity> response = iconRepository.findById(id);
        return response.orElse(null);
    }

    @Override
    public IconEntity findByName(String name) {
        Optional<IconEntity> response = iconRepository.findByName(name);
        return response.orElse(null);
    }

    @Override
    public List<IconDTO> getByFilters(String name, String date, List<String> countries, String order) {
        IconFiltersDTO filtersDTO = new IconFiltersDTO(name, date, countries, order);
        List<IconEntity> entityList = iconRepository.findAll(iconSpecification.getByFilters(filtersDTO));
        return iconMapper.entityList2DTOList(entityList, true);
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
