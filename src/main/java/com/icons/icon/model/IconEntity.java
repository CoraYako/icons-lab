package com.icons.icon.model;

import com.icons.country.model.CountryEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "icons")
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
    private boolean deleted = false;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "CREATED_AT")
    private LocalDate createdAt;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "ICONS_COUNTRIES",
            joinColumns = @JoinColumn(name = "ICON_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    )
    private List<CountryEntity> countries = new ArrayList<>();

    public IconEntity() {
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String value) {
        if (Objects.isNull(value) || value.trim().isEmpty())
            throw  new IllegalArgumentException("The icon image URL can't be empty");
        this.imageURL = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        if (Objects.isNull(value) || value.trim().isEmpty())
            throw new  IllegalArgumentException("The icon name can't be empty");
        this.name = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int value) {
        if (value < 1)
            throw  new  IllegalArgumentException("The icon's height can't be less than 1");
        this.height = value;
    }

    public String getHistoryDescription() {
        return historyDescription;
    }

    public void setHistoryDescription(String value) {
        if (!Objects.isNull(value) && !value.trim().isEmpty())
            this.historyDescription = value;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDate getCreationDate() {
        return createdAt;
    }

    public void setCreationDate(LocalDate value) {
        if (Objects.isNull(value))
            throw  new  IllegalArgumentException("The icon creation date can't be empty");
        this.createdAt = value;
    }

    public List<CountryEntity> getCountries() {
        return countries;
    }
}
