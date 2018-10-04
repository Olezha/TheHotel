package ua.olezha.hotel.web;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.olezha.hotel.model.GeoPoint;
import ua.olezha.hotel.model.Hotel;
import ua.olezha.hotel.repository.HotelRepository;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/management")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagementController {

    HotelRepository hotelRepository;

    @GetMapping
    public String reservations() {
        return "management-reservations";
    }

    @GetMapping("/rooms")
    public String rooms() {
        return "management-rooms";
    }

    @GetMapping("/hotels")
    public String hotels() {
        return "management-hotels";
    }

    @PostMapping("/hotel")
    public String editHotel(@Valid @ModelAttribute("hotel") HotelDto hotelDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "management-hotel-edit";

        hotelRepository.save(hotelDto.build());

        return "redirect:/management/hotels";
    }

    @GetMapping("/users")
    public String users() {
        return "management-users";
    }
}

@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class HotelDto extends Hotel {

    BigDecimal geoLongitude;

    BigDecimal geoLatitude;

    Hotel build() {
        return Hotel.builder()
                .id(getId())
                .name(getName())
                .address(getAddress())
                .geoPoint(GeoPoint.builder()
                        .id(getGeoPoint().getId())
                        .longitude(geoLongitude)
                        .latitude(geoLatitude)
                        .build())
                .description(getDescription())
                .photos(getPhotos())
                .rooms(getRooms())
                .build();
    }
}
