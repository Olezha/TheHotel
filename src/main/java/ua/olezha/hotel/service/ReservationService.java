package ua.olezha.hotel.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.olezha.hotel.model.Reservation;
import ua.olezha.hotel.model.Room;
import ua.olezha.hotel.model.User;
import ua.olezha.hotel.repository.ReservationRepository;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReservationService {

    ReservationRepository reservationRepository;

    public void book(Room room, User user) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        // TODO
        reservationRepository.save(reservation);
    }
}
