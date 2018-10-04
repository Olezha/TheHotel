package ua.olezha.hotel.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author  Oleh Shklyar
 */

@Data
@Builder
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private LocalDateTime checkIn, checkOut;

    private LocalDateTime created = LocalDateTime.now();

    private BigDecimal totalCost;

    private Boolean rejected = false;

    @Lob
    private String guestRemark;

    @Lob
    private String ownerRemark;
}
