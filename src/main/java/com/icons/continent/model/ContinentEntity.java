package com.icons.continent.model;

import com.icons.country.model.CountryEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CONTINENTS")
public class ContinentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String imageURL;
    private String name;
    @OneToMany(mappedBy = "continent", fetch = FetchType.LAZY)
    private List<CountryEntity> countries = new ArrayList<>();

    public ContinentEntity() {
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String value) {
        if (Objects.isNull(value) || value.trim().isEmpty())
            throw  new IllegalArgumentException("The image URL value cannot be null or empty");
        this.imageURL = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        if (Objects.isNull(value) || value.trim().isEmpty())
            throw   new IllegalArgumentException("The continent name value cannot be null or empty");
        this.name = value;
    }

    public List<CountryEntity> getCountries() {
        return countries;
    }
}