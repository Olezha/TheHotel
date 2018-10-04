package ua.olezha.hotel.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author  Oleh Shklyar
 */

@Entity
@Data
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

    Integer accommodates;

    BigDecimal price;
}
