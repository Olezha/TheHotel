package ua.olezha.hotel.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author  Oleh Shklyar
 */

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode @ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeoPoint {

    @Id
    @GeneratedValue
    Long id;

    Double longitude;

    Double latitude;
}
