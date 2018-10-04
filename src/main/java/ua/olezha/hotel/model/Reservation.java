package ua.olezha.hotel.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author  Oleh Shklyar
 */

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private User user;

    private Room room; // TODO: sinking about room vs rooms

    private LocalDateTime checkIn, checkOut;

    private LocalDateTime created = LocalDateTime.now();

    private BigDecimal totalCost;

    private Boolean rejected;

    @Lob
    private String guestRemark;

    @Lob
    private String ownerRemark;
}
