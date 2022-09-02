package com.icons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    private String id;

    private String image;

    @NotEmpty(message = "The country name can't be null or empty.")
    @NotBlank(message = "Must provide a valid name for the country, not whitespaces.")
    private String denomination;

    private BigInteger population;

    private Double area;

    @NotNull(message = "Must specify the continent which country belongs.")
    private ContinentDTO continent;

    private List<IconDTO> icons = new ArrayList<>();
}
