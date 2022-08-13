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

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "continent_id", insertable = false, updatable = false)
    private ContinentEntity continent;

/*    @Column(name = "continent_id", nullable = false)
    private String continentId;*/

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_icon_country", joinColumns = @JoinColumn(name = "country_id"), inverseJoinColumns = @JoinColumn(name = "icon_id"))
    private List<IconEntity> icons = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CountryEntity other = (CountryEntity) obj;
        return other.id.equals(this.id);
    }
}