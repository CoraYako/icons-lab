package com.icons.icon.model;

import com.icons.country.model.CountryEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ICONS")
@SQLDelete(sql = "UPDATE icons SET deleted = true WHERE id=?")
@SQLRestriction("deleted <> false")
public class IconEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String imageURL;
    private String name;
    private int height;
    private String historyDescription;
    private final boolean deleted = false;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "CREATED_AT")
    private LocalDate createdAt;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "ICONS_COUNTRIES",
            joinColumns = @JoinColumn(name = "ICON_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    )
    private Set<CountryEntity> countries = new HashSet<>();

    public IconEntity() {
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int value) {
        this.height = value;
    }

    public String getHistoryDescription() {
        return historyDescription;
    }

    public void setHistoryDescription(String value) {
        this.historyDescription = value;
    }

    public LocalDate getCreationDate() {
        return createdAt;
    }

    public void setCreationDate(LocalDate value) {
        this.createdAt = value;
    }

    public Set<CountryEntity> getCountries() {
        return countries;
    }
}
