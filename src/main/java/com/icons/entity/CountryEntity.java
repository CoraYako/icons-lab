package com.icons.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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

    private String denomination;

    private BigInteger population;

    private Double area;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "continent_id")
    private ContinentEntity continent;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
    @JoinTable(name = "rel_icon_country", joinColumns = @JoinColumn(name = "country_id"), inverseJoinColumns = @JoinColumn(name = "icon_id"))
    private List<IconEntity> icons = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;
}