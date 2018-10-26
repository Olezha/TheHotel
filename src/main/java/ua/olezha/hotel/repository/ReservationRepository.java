package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.olezha.hotel.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Oleh Shklyar
 */

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByRoom_Hotel_IdAndCheckOutIsAfterAndCheckInIsBeforeAndUserIsNotNullOrCreatedIsAfter(
            Long hotelId,
            LocalDateTime from, LocalDateTime to,
            LocalDateTime preReservationTime);
}
