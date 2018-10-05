package ua.olezha.hotel.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

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

    @Column(precision = 10, scale = 7)
    BigDecimal longitude;

    @Column(precision = 10, scale = 7)
    BigDecimal latitude;
}
