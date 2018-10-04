package ua.olezha.hotel.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author  Oleh Shklyar
 */

@Data
@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private String number;

    @Lob
    private String description;

    @ElementCollection
    private List<String> photos;

    private Integer accommodates;

    private BigDecimal price;
}
