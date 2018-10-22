package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.olezha.hotel.model.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHotel_Id(Long hotelId);

    @Query(nativeQuery = true)
    List<Room> availableRooms(Long hotelId,
                              Integer persons,
                              Integer preReservationDurationMinute,
                              LocalDateTime checkIn, LocalDateTime checkOut);
}
