package com.icons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    private String id;
    private String image;
    private String denomination;
    private BigInteger population;
    private Double area;
    /*private String continentId;*/
    private List<IconDTO> icons;
}
