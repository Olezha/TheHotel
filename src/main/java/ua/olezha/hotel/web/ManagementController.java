package ua.olezha.hotel.web;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.olezha.hotel.model.*;
import ua.olezha.hotel.repository.GeoPointRepository;
import ua.olezha.hotel.repository.ReservationRepository;
import ua.olezha.hotel.repository.RoomRepository;
import ua.olezha.hotel.service.HotelService;
import ua.olezha.hotel.service.ReservationService;
import ua.olezha.hotel.util.MathUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Oleh Shklyar
 */

@Slf4j
@Controller
@RequestMapping(value = "/management")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagementController {

    HotelService hotelService;

    RoomRepository roomRepository;

    GeoPointRepository geoPointRepository;

    ReservationRepository reservationRepository;

    ReservationService reservationService;

    @GetMapping
    public String reservations(Model model) {
        LocalDateTime now = LocalDateTime.now();
        Hotel hotel = hotelService.first();
        if (hotel != null) {
            Long hotelId = hotel.getId();
            model.addAttribute("rooms", roomRepository.findAllByHotel_Id(hotelId));
            model.addAttribute("reservations", reservationService.reservations(
                    hotelId, now, now.plusMonths(2)));
        }
        return "management/reservations";
    }

    @GetMapping("/hotels")
    public void hotels(Model model) {
        model.addAttribute("hotels", hotelService.hotels());
    }

    @GetMapping("/hotel/{hotelId}/rooms")
    public String rooms(Model model, @PathVariable Long hotelId) {
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("rooms", roomRepository.findAllByHotel_Id(hotelId));
        return "management/rooms";
    }

    @GetMapping({"/hotels/add", "/hotels/{id}/edit"})
    public String editHotel(Model model, @PathVariable(required = false) Long id) {
        if (id == null)
            model.addAttribute("hotel", new HotelDto());
        else
            model.addAttribute("hotel", HotelDto.valueOf(hotelService.hotel(id)));

        return "management/hotel-edit";
    }

    @PostMapping("/hotels")
    public String editHotel(@Valid @ModelAttribute("hotel") HotelDto hotelDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "management/hotel-edit";

        Hotel hotel = hotelDto.build();
        geoPointRepository.save(hotel.getGeoPoint());
        hotelService.save(hotel);

        return "redirect:/management/hotels";
    }

    @GetMapping({"/hotel/{hotelId}/room/add", "/hotel/{hotelId}/room/{id}/edit"})
    public String editHotelRoom(Model model, @PathVariable Long hotelId,
                                @PathVariable(required = false) Long id) {
        model.addAttribute("hotelId", hotelId);

        if (id != null)
            roomRepository.findById(id)
                    .ifPresent(r -> model.addAttribute("room", RoomDto.valueOf(r)));

        if (!model.containsAttribute("room"))
            model.addAttribute("room", new RoomDto());

        return "management/room-edit";
    }

    @PostMapping("/hotel/{hotelId}/rooms")
    public String editRoom(Model model, @PathVariable Long hotelId,
                           @Valid @ModelAttribute("room") RoomDto roomDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hotelId", hotelId);
            return "management/room-edit";
        }

        Room room = roomDto.build();
        room.setHotel(hotelService.hotel(hotelId));
        roomRepository.save(room);

        return "redirect:/management/hotel/" + hotelId + "/rooms";
    }

    @GetMapping("/reservation/{id}")
    public String reservation(Model model, @PathVariable Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (!reservation.isPresent())
            throw new RuntimeException("Reservation isAbsent");

        if (reservation.get().getUser() == null)
            throw new RuntimeException("Reservation isPreReservation");

        model.addAttribute("reservation", reservation.get());
        return "management/reservation";
    }
}

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class HotelDto {

    Long id;

    @NotEmpty
    String name;

    String address;

    String description;

    Long geoPointId;

    @NotEmpty
    String geoLongitude;

    @NotEmpty
    String geoLatitude;

    List<String> photos = new ArrayList<>();

    static HotelDto valueOf(Hotel hotel) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.id = hotel.getId();
        hotelDto.name = hotel.getName();
        hotelDto.address = hotel.getAddress();
        hotelDto.description = hotel.getDescription();
        if (hotel.getGeoPoint() != null) {
            hotelDto.geoPointId = hotel.getGeoPoint().getId();
            hotelDto.geoLongitude = hotel.getGeoPoint().getLongitude().toString();
            hotelDto.geoLatitude = hotel.getGeoPoint().getLatitude().toString();
        }
        if (hotel.getPhotos() != null)
            hotelDto.photos = hotel.getPhotos();
        return hotelDto;
    }

    Hotel build() {
        return Hotel.builder()
                .id(id)
                .name(name)
                .address(address)
                .geoPoint(GeoPoint.builder()
                        .id(geoPointId)
                        .longitude(Double.parseDouble(
                                MathUtils.cleanNumber(geoLongitude)))
                        .latitude(Double.parseDouble(
                                MathUtils.cleanNumber(geoLatitude)))
                        .build())
                .description(description)
                .photos(photos)
                .build();
    }
}

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class RoomDto {

    Long id;

    @NotEmpty
    String number;

    String description;

    Integer persons;

    @NonNull
    BigDecimal price;

    List<String> photos = new ArrayList<>();

    static RoomDto valueOf(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.id = room.getId();
        roomDto.number = room.getNumber();
        roomDto.description = room.getDescription();
        roomDto.persons = room.getPersons();
        roomDto.price = room.getPrice();
        if (room.getPhotos() != null)
            roomDto.photos = room.getPhotos();
        return roomDto;
    }

    Room build() {
        return Room.builder()
                .id(id)
                .number(number)
                .description(description)
                .persons(persons)
                .price(price)
                .photos(photos)
                .build();
    }
}
