package com.icons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContinentDTO {

    private String id;

    private String image;

    @NotEmpty(message = "The continent name can't be null or empty.")
    @NotBlank(message = "Must provide a valid name for continent, not whitespaces.")
    private String denomination;

    private List<CountryDTO> countries = new ArrayList<>();
}
