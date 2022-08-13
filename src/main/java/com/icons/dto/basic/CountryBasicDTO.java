package com.icons.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryBasicDTO {

    private String id;
    private String image;
    private String denomination;
    private BigInteger population;
    private Double area;
}
