package com.icons.service.implement;

import com.icons.dto.IconDTO;
import com.icons.dto.filters.IconFiltersDTO;
import com.icons.entity.CountryEntity;
import com.icons.entity.IconEntity;
import com.icons.exception.ParamNotFoundException;
import com.icons.mapper.IconMapper;
import com.icons.repository.IconRepository;
import com.icons.repository.spec.IconSpecification;
import com.icons.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IconServiceImplement implements IconService {

    private final IconRepository iconRepository;
    private final IconSpecification iconSpecification;
    private final IconMapper iconMapper;
    private final CountryServiceImplement countryServiceImplement;

    @Autowired
    public IconServiceImplement(IconRepository iconRepository, IconSpecification iconSpecification, IconMapper iconMapper, CountryServiceImplement countryServiceImplement) {
        this.iconRepository = iconRepository;
        this.iconSpecification = iconSpecification;
        this.iconMapper = iconMapper;
        this.countryServiceImplement = countryServiceImplement;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public IconDTO save(IconDTO dto) {
        IconEntity repositoryResponse = getEntityByName(dto.getDenomination());

        if (repositoryResponse != null && dto.getDenomination().equalsIgnoreCase(repositoryResponse.getDenomination())) {
            throw new EntityExistsException("The icon already exist.");
        }

        IconEntity entity = iconMapper.DTO2Entity(dto, false);
        IconEntity entitySaved = iconRepository.save(entity);

        List<CountryEntity> countryEntityList = new ArrayList<>();

        dto.getCountries().forEach(country -> {
            var countryFound = countryServiceImplement.getEntityById(country.getId());
            if (countryFound != null) {
                var countryWithIcon = countryServiceImplement.addIcon(countryFound, entitySaved);
                countryEntityList.add(countryWithIcon);
            }
        });

        entity.setCountries(countryEntityList);
        return iconMapper.entity2DTO(iconRepository.save(entity), true);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public IconDTO update(String id, IconDTO dto) {
        IconEntity entityFound = getEntityById(id);

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
    @Transactional(readOnly = true)
    public IconEntity getEntityById(String id) {
        Optional<IconEntity> response = iconRepository.findById(id);
        if (response.isEmpty()) {
            throw new ParamNotFoundException("The icon can't be found or doesn't exist.");
        }
        return response.get();
    }

    @Override
    @Transactional(readOnly = true)
    public IconEntity getEntityByName(String name) {
        Optional<IconEntity> response = iconRepository.findByName(name);
        return response.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IconDTO> getByFilters(String name, String date, List<String> countries, String order) {
        IconFiltersDTO filtersDTO = new IconFiltersDTO(name, date, countries, order);
        List<IconEntity> entityList = iconRepository.findAll(iconSpecification.getByFilters(filtersDTO));
        return iconMapper.entityList2DTOList(entityList, true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IconDTO> getAll() {
        List<IconEntity> entityList = iconRepository.findAll();
        return iconMapper.entityList2DTOList(entityList, true);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public void delete(String id) {
        iconRepository.deleteById(id);
    }
}
