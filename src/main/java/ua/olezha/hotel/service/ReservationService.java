package ua.olezha.hotel.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.olezha.hotel.model.Reservation;
import ua.olezha.hotel.model.Room;
import ua.olezha.hotel.repository.ReservationRepository;
import ua.olezha.hotel.repository.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author Oleh Shklyar
 */

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationService {

    @Value("${pre-reservation-duration-minute}")
    Integer preReservationDurationMinute;

    final ReservationRepository reservationRepository;

    final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public List<Reservation> reservations(Long hotelId,
                                                     LocalDateTime from, LocalDateTime to) {
        return reservationRepository
                .findAllByRoom_Hotel_IdAndCheckOutIsAfterAndCheckInIsBeforeAndUserIsNotNullOrCreatedIsAfter(
                        hotelId, from, to, LocalDateTime.now().minusMinutes(preReservationDurationMinute));
    }

    public List<Room> availableRooms(Long hotelId,
                              LocalDateTime checkIn, LocalDateTime checkOut,
                              Integer persons) {
        return roomRepository.availableRooms(hotelId, persons, preReservationDurationMinute, checkIn, checkOut);
    }

    public Reservation preReserve(Room room,
                     LocalDateTime checkIn, LocalDateTime checkOut) {
        return reservationRepository.save(Reservation.builder()
                .room(room)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .totalCost(room.getPrice().multiply(
                        BigDecimal.valueOf(ChronoUnit.DAYS.between(checkIn, checkOut))))
                .build());
    }
}
