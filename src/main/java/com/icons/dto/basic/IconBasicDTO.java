package com.icons.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IconBasicDTO {

    private String id;
    private String image;
    private String denomination;
    private String creation;
    private Integer height;
    private String history;
}
