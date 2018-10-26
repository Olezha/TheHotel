package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.olezha.hotel.model.Hotel;

import java.util.List;

/**
 * @author Oleh Shklyar
 */

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findAll();
}
