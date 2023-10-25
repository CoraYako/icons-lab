package com.icons.auth.model.mapper;

import com.icons.auth.model.Role;
import com.icons.auth.model.UserEntity;
import com.icons.auth.model.dto.RegistrationRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(RegistrationRequestDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        entity.setRole(Role.USER);
        entity.setEnabled(true);
        return entity;
    }
}
