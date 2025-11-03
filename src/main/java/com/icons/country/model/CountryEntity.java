package com.icons.country.model;

import com.icons.continent.model.ContinentEntity;
import com.icons.icon.model.IconEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private boolean deleted = false;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "CONTINENT_ID")
    private ContinentEntity continent;
    @ManyToMany(mappedBy = "COUNTRIES", fetch = FetchType.EAGER)
    private List<IconEntity> icons = new ArrayList<>();

    public CountryEntity() {
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String value) {
        if (Objects.isNull(value) || value.trim().isEmpty())
            throw new IllegalArgumentException("The image URL cannot be empty or null");
        this.imageURL = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        if (Objects.isNull(value) || value.trim().isEmpty())
            throw new IllegalArgumentException("The country name cannot be empty or null");
        this.name = value;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long value) {
        if (value < 1)
            throw new IllegalArgumentException("The country's population cannot be less than 1");
        this.population = value;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double value) {
        if (value < 1)
            throw new IllegalArgumentException("The country's area cannot be less than 1");
        this.area = value;
    }

    public ContinentEntity getContinent() {
        return continent;
    }

    public void setContinent(ContinentEntity continent) {
        if (Objects.isNull(continent))
            throw new IllegalArgumentException("The country's continent cannot be null");
        this.continent = continent;
    }

    public List<IconEntity> getIcons() {
        return icons;
    }

    public void appendIcon(IconEntity icon) {
        if (Objects.isNull(icon))
            throw new IllegalArgumentException("The country's icon cannot be null");
        this.icons.add(icon);
    }

    public Boolean isDeleted() {
        return deleted;
    }
}