package com.icons.country.model;

import com.icons.continent.model.ContinentEntity;
import com.icons.icon.model.IconEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "countries")
@SQLDelete(sql = "UPDATE countries SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class CountryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String image;
    private String name;
    private long population;
    private double area;
    private boolean deleted = false;
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "continent_id")
    private ContinentEntity continent;
    @ManyToMany(mappedBy = "countries", fetch = FetchType.EAGER)
    private List<IconEntity> icons;

    public CountryEntity(String id, String image, String name, long population, double area,
                         ContinentEntity continent, List<IconEntity> icons) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.population = population;
        this.area = area;
        this.continent = continent;
        this.icons = icons;
    }

    public CountryEntity() {
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

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long value) {
        if (value > 0)
            this.population = value;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double value) {
        if (value > 1)
            this.area = value;
    }

    public ContinentEntity getContinent() {
        return continent;
    }

    public void setContinent(ContinentEntity continent) {
        this.continent = continent;
    }

    public List<IconEntity> getIcons() {
        return Objects.isNull(icons) ? icons = new ArrayList<>() : icons;
    }

    public void setIcons(List<IconEntity> icons) {
        this.icons = icons;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}