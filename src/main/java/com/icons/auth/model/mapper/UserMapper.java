package com.icons.auth.model.mapper;

import com.icons.auth.model.UserEntity;
import com.icons.auth.model.dto.RegistrationRequestDTO;
import jakarta.validation.constraints.NotNull;

public interface UserMapper {

    UserEntity toEntity(@NotNull RegistrationRequestDTO dto);
}
