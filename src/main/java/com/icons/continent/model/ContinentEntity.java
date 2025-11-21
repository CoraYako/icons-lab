package com.icons.continent.model;

import com.icons.country.model.CountryEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CONTINENTS")
public class ContinentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String imageURL;
    private String name;
    @OneToMany(mappedBy = "continent", fetch = FetchType.LAZY)
    private final Set<CountryEntity> countries = new HashSet<>();

    public ContinentEntity() {
    }

    public ContinentEntity(String id, String imageURL, String name) {
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
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

    public Set<CountryEntity> getCountries() {
        return countries;
    }
}