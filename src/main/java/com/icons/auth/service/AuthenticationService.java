package com.icons.auth.service;

import com.icons.auth.model.dto.AuthenticationRequestDTO;
import com.icons.auth.model.dto.AuthenticationResponseDTO;
import com.icons.auth.model.dto.RegistrationRequestDTO;
import jakarta.validation.constraints.NotNull;

public interface AuthenticationService {
    void register(@NotNull RegistrationRequestDTO requestDTO);

    AuthenticationResponseDTO authenticate(@NotNull AuthenticationRequestDTO requestDTO);
}
