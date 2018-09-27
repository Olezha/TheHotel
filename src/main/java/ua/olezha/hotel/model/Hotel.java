package ua.olezha.hotel.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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

    private GeoPoint geoPoint;

    @Lob
    private String description;

    private List<String> photos;

    private List<Room> rooms;
}
