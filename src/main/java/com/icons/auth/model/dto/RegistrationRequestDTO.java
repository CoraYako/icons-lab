package com.icons.auth.model.dto;

import com.icons.util.ApiUtils;
import jakarta.validation.constraints.*;

public record RegistrationRequestDTO(
        @NotEmpty @NotBlank String firstName,
        @NotEmpty @NotBlank String lastName,
        @NotEmpty @NotBlank @Email String email,
        @NotEmpty @NotBlank @Size(min = 8, max = 15) @Pattern(regexp = ApiUtils.REGEX_PATTERN) String password
) {
}
