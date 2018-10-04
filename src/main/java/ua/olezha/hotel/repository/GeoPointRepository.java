package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.olezha.hotel.model.GeoPoint;

public interface GeoPointRepository extends JpaRepository<GeoPoint, Long> {
}
