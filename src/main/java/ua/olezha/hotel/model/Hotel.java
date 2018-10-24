package ua.olezha.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

/**
 * @author Oleh Shklyar
 */

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "rooms")
@ToString(exclude = "rooms")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hotel {

    @Id
    @GeneratedValue
    Long id;

    String name;

    String address;

    @ManyToOne
    GeoPoint geoPoint;

    @Lob
    String description;

    @ElementCollection
    List<String> photos;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    List<Room> rooms;
}
