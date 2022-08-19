package com.icons.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "continents")
public class ContinentEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String image;

    @NotNull
    @NotEmpty(message = "Provide a name to the continent.")
    private String denomination;

    @OneToMany(mappedBy = "continent",fetch = FetchType.LAZY/*, cascade = {CascadeType.REFRESH}*/)
    private List<CountryEntity> countries = new ArrayList<>();
}