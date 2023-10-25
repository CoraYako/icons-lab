package com.icons.auth.model.dto;

import jakarta.validation.constraints.*;

public record RegistrationRequestDTO(
        @NotEmpty @NotBlank String firstName,
        @NotEmpty @NotBlank String lastName,
        @NotEmpty @NotBlank @Email String email,
        @NotEmpty @NotBlank @Size(min = 8, max = 15) String password
) {
}
