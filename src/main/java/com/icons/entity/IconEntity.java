package com.icons.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "icons")
@SQLDelete(sql = "UPDATE icons SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class IconEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String image;

    @NotNull
    @NotEmpty(message = "Description required.")
    private String denomination;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "creation_date")
    @NotNull(message = "Creation date needs to be set.")
    private LocalDate creation;

    private Integer height;

    private String history;

    @ManyToMany(mappedBy = "icons", fetch = FetchType.LAZY)
    @NotNull(message = "Provide the country/ies where the icon is present.")
    private List<CountryEntity> countries = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;
}
