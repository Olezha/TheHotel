package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.olezha.hotel.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
