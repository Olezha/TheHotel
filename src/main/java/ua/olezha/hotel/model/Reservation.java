package ua.olezha.hotel.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Class represents the hotel room pre-reservation and reservation.
 *
 * In the first step, the user makes a pre-reservation, and then enters personal data.
 * If the user has not entered personal data for an {pre-reservation-duration-minute} property,
 * then the pre-reservation is removed.
 *
 * After reservation the hotelier should contact with user and confirm or reject reservation.
 *
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

    @NotNull
    @ManyToOne
    Room room;

    @NotNull
    LocalDateTime checkIn;

    @NonNull
    LocalDateTime  checkOut;

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
