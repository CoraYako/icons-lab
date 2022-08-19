package com.icons.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "countries")
@SQLDelete(sql = "UPDATE countries SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class CountryEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String image;

    @NotNull
    @NotEmpty(message = "Must provide a name for the country.")
    private String denomination;

    private BigInteger population;

    @NotNull(message = "Area can't be null.")
    private Double area;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "continent_id")
    @NotNull(message = "Must specify a continent.")
    private ContinentEntity continent;

    /*@Column(name = "continent_id")
    @NotNull
    @NotEmpty(message = "Set the id of the continent.")
    private String continentId;*/

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_icon_country", joinColumns = @JoinColumn(name = "country_id"), inverseJoinColumns = @JoinColumn(name = "icon_id"))
    private List<IconEntity> icons = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;
}