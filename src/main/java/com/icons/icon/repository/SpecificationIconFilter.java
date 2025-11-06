package com.icons.icon.repository;

import com.icons.AbstractSpecificationFilter;
import com.icons.icon.model.IconEntity;
import com.icons.icon.model.dto.IconFilterRequestDTO;
import com.icons.icon.repository.filters.IconFilterStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecificationIconFilter extends
        AbstractSpecificationFilter<IconEntity, IconFilterRequestDTO, IconFilterStrategy> {

    protected SpecificationIconFilter(List<IconFilterStrategy> filterStrategies) {
        super(filterStrategies);
    }
}
