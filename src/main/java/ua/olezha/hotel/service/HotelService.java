package ua.olezha.hotel.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.olezha.hotel.model.Hotel;
import ua.olezha.hotel.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelService {

    HotelRepository hotelRepository;

    public Hotel first() {
        return hotelRepository.findAll().get(0);
    }

    public Hotel hotel(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (!hotel.isPresent()) throw new RuntimeException("Hotel isAbsent");
        return hotel.get();
    }

    public List<Hotel> hotels() {
        return hotelRepository.findAll();
    }

    public void save(Hotel hotel) {
        hotelRepository.save(hotel);
    }
}
