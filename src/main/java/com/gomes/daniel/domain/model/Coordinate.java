package com.gomes.daniel.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Coordinate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_coord")
    private Long id;

    public Coordinate(@NonNull double lat, @NonNull double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Coordinate() {
    }

    @NonNull
    private double lat;

    @NonNull
    private double lng;
}
