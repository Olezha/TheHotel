package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.olezha.hotel.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
