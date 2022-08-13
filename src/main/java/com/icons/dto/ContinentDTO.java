package com.icons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContinentDTO {

    private String id;
    private String image;
    private String denomination;
    private List<CountryDTO> countries;
}
