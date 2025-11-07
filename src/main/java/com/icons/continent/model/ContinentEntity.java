package com.icons.continent.model;

import com.icons.country.model.CountryEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        this.imageURL = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public List<CountryEntity> getCountries() {
        return countries;
    }
}