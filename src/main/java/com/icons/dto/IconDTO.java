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
public class IconDTO {

    private String id;

    private String image;

    @NotEmpty(message = "A name fot the icon is required.")
    @NotBlank(message = "The icon name can't be just whitespaces.")
    private String denomination;

    @NotEmpty(message = "Creation date can't be null or empty.")
    @NotBlank(message = "A creation date needs to be set.")
    private String creation;

    private Integer height;

    private String history;

    private List<CountryDTO> countries = new ArrayList<>();
}
