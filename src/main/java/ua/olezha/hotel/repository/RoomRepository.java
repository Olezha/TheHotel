package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.olezha.hotel.model.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHotel_Id(Long hotelId);
}
