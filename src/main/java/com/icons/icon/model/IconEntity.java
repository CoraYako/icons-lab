package com.icons.icon.model;

import com.icons.country.model.CountryEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "icons")
@SQLDelete(sql = "UPDATE icons SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class IconEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String image;
    private String name;
    private int height;
    private String historyDescription;
    private boolean deleted = false;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "icons_countries",
            joinColumns = @JoinColumn(name = "icon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
    )
    private List<CountryEntity> countries;

    public IconEntity(String id,
                      String image,
                      String name,
                      int height,
                      String historyDescription,
                      LocalDate creationDate,
                      List<CountryEntity> countries) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.height = height;
        this.historyDescription = historyDescription;
        this.creationDate = creationDate;
        this.countries = countries;
    }

    public IconEntity() {
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int value) {
        if (value > 0)
            this.height = value;
    }

    public String getHistoryDescription() {
        return historyDescription;
    }

    public void setHistoryDescription(String value) {
        if (!Objects.isNull(value) && !value.trim().isEmpty())
            this.historyDescription = value;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<CountryEntity> getCountries() {
        return Objects.isNull(countries) ? countries = new ArrayList<>() : countries;
    }

    public void setCountries(List<CountryEntity> countries) {
        this.countries = countries;
    }
}
