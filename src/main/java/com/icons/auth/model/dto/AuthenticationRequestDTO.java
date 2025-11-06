package com.icons.auth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequestDTO(
        @NotEmpty @NotBlank @Email String email,
        @NotEmpty @NotBlank String password
) {
}
