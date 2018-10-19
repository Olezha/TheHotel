package ua.olezha.hotel.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.olezha.hotel.model.Room;
import ua.olezha.hotel.repository.HotelRepository;
import ua.olezha.hotel.repository.RoomRepository;
import ua.olezha.hotel.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeController {

    final HotelRepository hotelRepository;

    final RoomRepository roomRepository;

    final ReservationService reservationService;

    @Value("${hotel.checkInHour}")
    int checkInHour;

    @Value("${hotel.checkInMinute}")
    int checkInMinute;

    @Value("${hotel.checkOutHour}")
    int checkOutHour;

    @Value("${hotel.checkOutMinute}")
    int checkOutMinute;

    public HomeController(HotelRepository hotelRepository,
                          RoomRepository roomRepository,
                          ReservationService reservationService) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("hotels", hotelRepository.findAll());
        return "home";
    }

    @RequestMapping("/hotel/{hotelId}/browse")
    public String browse(Model model, @PathVariable Long hotelId,
                         @ModelAttribute AvailabilityFilterDto filter) {
        if (filter.getCheckInCheckOut() == null) {
            model.addAttribute("filter", new AvailabilityFilterDto());
            model.addAttribute("allRooms", roomRepository.findAllByHotel_Id(hotelId));
        }
        else {
            model.addAttribute("filter", filter);
            model.addAttribute("availableRooms",
                    roomRepository.availableRooms(
                            hotelId,
                            filter.getCheckIn(checkInHour, checkInMinute),
                            filter.getCheckOut(checkOutHour, checkOutMinute),
                            filter.getPersons()));
        }

        return "browse";
    }

    @PostMapping("/hotel/{hotelId}/room/{roomId}/reserve")
    public String reserve(@PathVariable Long roomId, @ModelAttribute AvailabilityFilterDto filter) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (!room.isPresent())
            throw new RuntimeException();

        reservationService.reserve(room.get(),
                null,
                filter.getCheckIn(checkInHour, checkInMinute),
                filter.getCheckOut(checkOutHour, checkOutMinute),
                null);
        return "reserve";
    }
}

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class AvailabilityFilterDto {

    String checkInCheckOut;

    Integer persons;

    LocalDateTime getCheckIn(int checkInHour, int checkInMinute) {
        return parseDate(checkInCheckOut.split(" - ")[0])
                .atTime(checkInHour, checkInMinute);
    }

    LocalDateTime getCheckOut(int checkOutHour, int checkOutMinute) {
        return parseDate(checkInCheckOut.split(" - ")[1])
                .atTime(checkOutHour, checkOutMinute);
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date,
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
