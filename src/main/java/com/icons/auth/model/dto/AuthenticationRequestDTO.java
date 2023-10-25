package com.icons.auth.model.dto;

import com.icons.util.ApiUtils;
import jakarta.validation.constraints.*;

public record AuthenticationRequestDTO(
        @NotEmpty @NotBlank @Email String email,
        @NotEmpty @NotBlank @Pattern(regexp = ApiUtils.REGEX_PATTERN) String password

) {
}
