package ua.olezha.hotel.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author  Oleh Shklyar
 */

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode @ToString
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private LocalDateTime checkIn, checkOut;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    private BigDecimal totalCost;

    @Builder.Default
    private Boolean rejected = false;

    @Lob
    private String guestRemark;

    @Lob
    private String ownerRemark;
}
