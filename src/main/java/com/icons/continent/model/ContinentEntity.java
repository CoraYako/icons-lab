package com.icons.continent.model;

import com.icons.country.model.CountryEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "continents")
public class ContinentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String image;
    private String name;
    @OneToMany(
            mappedBy = "continent",
            fetch = FetchType.LAZY
    )
    private List<CountryEntity> countries;

    public ContinentEntity() {
    }

    public ContinentEntity(String id, String image, String name, List<CountryEntity> countries) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.countries = countries;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String value) {
        if (!Objects.isNull(value) && !value.trim().isEmpty())
            this.image = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        if (!Objects.isNull(value) && !value.trim().isEmpty())
            this.name = value;
    }

    public List<CountryEntity> getCountries() {
        return Objects.isNull(countries) ? countries = new ArrayList<>() : countries;
    }
}