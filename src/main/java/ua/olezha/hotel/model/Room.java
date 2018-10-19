package ua.olezha.hotel.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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
public class Room {

    @Id
    @GeneratedValue
    Long id;

    String number;

    @Lob
    String description;

    @ElementCollection
    List<String> photos;

    Integer persons;

    BigDecimal price;

    @ManyToOne
    Hotel hotel;
}
