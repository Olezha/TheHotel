package ua.olezha.hotel.web;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.olezha.hotel.model.GeoPoint;
import ua.olezha.hotel.model.Hotel;
import ua.olezha.hotel.model.Room;
import ua.olezha.hotel.repository.GeoPointRepository;
import ua.olezha.hotel.repository.HotelRepository;
import ua.olezha.hotel.repository.RoomRepository;
import ua.olezha.hotel.util.MathUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(value = "/management")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagementController {

    HotelRepository hotelRepository;

    RoomRepository roomRepository;

    GeoPointRepository geoPointRepository;

    @GetMapping
    public String reservations() {
        return "management/reservations";
    }

    @GetMapping("/hotel/{hotelId}/rooms")
    public String rooms(Model model, @PathVariable Long hotelId) {
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("rooms", roomRepository.findAllByHotel_Id(hotelId));
        return "management/rooms";
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

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (!hotel.isPresent())
            throw new RuntimeException();

        Room room = roomDto.build();
        room.setHotel(hotel.get());
        roomRepository.save(room);

        return "redirect:/management/hotel/" + hotelId + "/rooms";
    }

    @GetMapping("/hotels")
    public void hotels(Model model) {
        model.addAttribute("hotels", hotelRepository.findAll());
    }

    @GetMapping({"/hotels/add", "/hotels/{id}/edit"})
    public String editHotel(Model model, @PathVariable(required = false) Long id) {
        if (id != null)
            hotelRepository.findById(id)
                    .ifPresent(h -> model.addAttribute("hotel", HotelDto.valueOf(h)));

        if (!model.containsAttribute("hotel"))
            model.addAttribute("hotel", new HotelDto());

        return "management/hotel-edit";
    }

    @PostMapping("/hotels")
    public String editHotel(@Valid @ModelAttribute("hotel") HotelDto hotelDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "management/hotel-edit";

        Hotel hotel = hotelDto.build();
        geoPointRepository.save(hotel.getGeoPoint());
        hotelRepository.save(hotel);

        return "redirect:/management/hotels";
    }

    @GetMapping("/users")
    public void users() {
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

    String geoLongitude;

    String geoLatitude;

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

    Integer accommodates;

    BigDecimal price;

    static RoomDto valueOf(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.id = room.getId();
        roomDto.number = room.getNumber();
        roomDto.description = room.getDescription();
        roomDto.accommodates = room.getAccommodates();
        roomDto.price = room.getPrice();
        return roomDto;
    }

    Room build() {
        return Room.builder()
                .id(id)
                .number(number)
                .description(description)
                .accommodates(accommodates)
                .price(price)
                .build();
    }
}
