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
import ua.olezha.hotel.repository.GeoPointRepository;
import ua.olezha.hotel.repository.HotelRepository;
import ua.olezha.hotel.util.StringUtils;

import javax.validation.Valid;
import java.math.BigDecimal;

@Slf4j
@Controller
@RequestMapping(value = "/management")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagementController {

    HotelRepository hotelRepository;

    GeoPointRepository geoPointRepository;

    @GetMapping
    public String reservations() {
        return "management/reservations";
    }

    @GetMapping("/rooms")
    public void rooms() {
    }

    @GetMapping("/hotels")
    public void hotels() {
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
        log.info("{}", bindingResult);
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
class HotelDto extends Hotel {

    Long geoPointId;

    BigDecimal geoLongitude;

    BigDecimal geoLatitude;

    static HotelDto valueOf(Hotel hotel) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(hotel.getId());
        hotelDto.setName(hotel.getName());
        hotelDto.setAddress(hotel.getAddress());
        hotelDto.setDescription(hotel.getDescription());
        if (hotel.getGeoPoint() != null) {
            hotelDto.geoPointId = hotel.getGeoPoint().getId();
            hotelDto.geoLongitude = hotel.getGeoPoint().getLongitude();
            hotelDto.geoLatitude = hotel.getGeoPoint().getLatitude();
        }
        return hotelDto;
    }

    Hotel build() {
        return Hotel.builder()
                .id(getId())
                .name(getName())
                .address(getAddress())
                .geoPoint(GeoPoint.builder()
                        .id(geoPointId)
                        .longitude(geoLongitude)
                        .latitude(geoLatitude)
                        .build())
                .description(getDescription())
                .photos(getPhotos())
                .rooms(getRooms())
                .build();
    }

    public String getGeoLongitude() {
        return geoLongitude == null ? null : geoLongitude.toString();
    }

    public void setGeoLongitude(String geoLongitude) {
        this.geoLongitude = new BigDecimal(
                cutGeoString(
                        StringUtils.cleanNumbersString(geoLongitude)));
    }

    public String getGeoLatitude() {
        return geoLatitude == null ? null : geoLatitude.toString();
    }

    public void setGeoLatitude(String geoLatitude) {
        this.geoLatitude = new BigDecimal(
                cutGeoString(
                        StringUtils.cleanNumbersString(geoLatitude)));
    }

    private String cutGeoString(String geoString) {
        if (geoString.charAt(0) == '-' && geoString.length() > 12)
            geoString = geoString.substring(0, 11);
        else if (geoString.length() > 11)
            geoString = geoString.substring(0, 10);
        return geoString;
    }
}
