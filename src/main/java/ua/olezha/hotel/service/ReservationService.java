package ua.olezha.hotel.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.olezha.hotel.model.Reservation;
import ua.olezha.hotel.model.Room;
import ua.olezha.hotel.model.User;
import ua.olezha.hotel.repository.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReservationService {

    ReservationRepository reservationRepository;

    public void book(Room room, User user,
                     LocalDateTime checkIn, LocalDateTime checkOut,
                     String guestRemark) {
        reservationRepository.save(Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .guestRemark(guestRemark)
                .totalCost(room.getPrice().multiply(
                        BigDecimal.valueOf(ChronoUnit.DAYS.between(checkIn, checkOut))))
                .build());
    }
}
