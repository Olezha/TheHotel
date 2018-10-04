package ua.olezha.hotel.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author  Oleh Shklyar
 */

@Data
@Entity
public class Hotel {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;

    @ManyToOne
    private GeoPoint geoPoint;

    @Lob
    private String description;

    @ElementCollection
    private List<String> photos;

    @OneToMany
    private List<Room> rooms;
}
