package com.icons.country.model;

import com.icons.continent.model.ContinentEntity;
import com.icons.icon.model.IconEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COUNTIES")
@SQLDelete(sql = "UPDATE COUNTRIES SET deleted=true WHERE id=?")
@SQLRestriction("deleted <> false")
public class CountryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String imageURL;
    private String name;
    private long population;
    private double area;
    private final boolean deleted = false;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "CONTINENT_ID")
    private ContinentEntity continent;
    @ManyToMany(mappedBy = "COUNTRIES", fetch = FetchType.EAGER)
    private final Set<IconEntity> icons = new HashSet<>();

    public CountryEntity() {
    }

    public CountryEntity(String id, String imageURL, String name, long population, double area) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.population = population;
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String value) {
        this.imageURL = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long value) {
        this.population = value;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double value) {
        this.area = value;
    }

    public ContinentEntity getContinent() {
        return continent;
    }

    public void setContinent(ContinentEntity continent) {
        this.continent = continent;
    }

    public Set<IconEntity> getIcons() {
        return icons;
    }
}