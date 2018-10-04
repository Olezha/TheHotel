package ua.olezha.hotel.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    User user;

    @ManyToOne
    Room room;

    LocalDateTime checkIn, checkOut;

    @Builder.Default
    LocalDateTime created = LocalDateTime.now();

    BigDecimal totalCost;

    @Builder.Default
    Boolean rejected = false;

    @Lob
    String guestRemark;

    @Lob
    String ownerRemark;
}
