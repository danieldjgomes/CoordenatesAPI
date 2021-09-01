package com.gomes.daniel.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Coordinate implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_coord")
    private Long id;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

    public Coordinate(@NonNull double lat, @NonNull double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Coordinate() {
    }


}
