package ua.olezha.hotel.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.olezha.hotel.model.Reservation;
import ua.olezha.hotel.model.Room;
import ua.olezha.hotel.model.User;
import ua.olezha.hotel.repository.HotelRepository;
import ua.olezha.hotel.repository.ReservationRepository;
import ua.olezha.hotel.repository.RoomRepository;
import ua.olezha.hotel.repository.UserRepository;
import ua.olezha.hotel.service.ReservationService;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author Oleh Shklyar
 */

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeController {

    @Value("${hotel.checkInHour}")
    int checkInHour;

    @Value("${hotel.checkInMinute}")
    int checkInMinute;

    @Value("${hotel.checkOutHour}")
    int checkOutHour;

    @Value("${hotel.checkOutMinute}")
    int checkOutMinute;

    final HotelRepository hotelRepository;

    final RoomRepository roomRepository;

    final ReservationRepository reservationRepository;

    final ReservationService reservationService;

    final UserRepository userRepository;

    public HomeController(HotelRepository hotelRepository,
                          RoomRepository roomRepository,
                          ReservationService reservationService,
                          ReservationRepository reservationRepository,
                          UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("hotels", hotelRepository.findAll());
        model.addAttribute("filter", new AvailabilityFilterDto());
        return "home";
    }

    @RequestMapping("/hotel/{hotelId}/browse")
    public String browse(Model model, @PathVariable Long hotelId,
                         @ModelAttribute AvailabilityFilterDto filter) {
        if (filter.getCheckInCheckOut() == null || filter.isSameDay()) {
            if (filter.getCheckInCheckOut() != null)
                model.addAttribute("message", "message.same-day-reservation");
            model.addAttribute("filter", filter);
            model.addAttribute("allRooms", roomRepository.findAllByHotel_Id(hotelId));
        } else {
            model.addAttribute("filter", filter);
            model.addAttribute("availableRooms",
                    reservationService.availableRooms(
                            hotelId,
                            filter.getCheckIn(checkInHour, checkInMinute),
                            filter.getCheckOut(checkOutHour, checkOutMinute),
                            filter.getPersons()));
        }

        return "browse";
    }

    @PostMapping("/hotel/{hotelId}/room/{roomId}/reserve")
    public String reservation(@PathVariable Long roomId,
                              @PathVariable Long hotelId,
                              @ModelAttribute AvailabilityFilterDto filter, Model model) {
        if (filter.isSameDay())
            return browse(model, hotelId, filter);

        Optional<Room> room = roomRepository.findById(roomId);
        if (!room.isPresent())
            throw new RuntimeException("Room isAbsent");

        Reservation reservation = reservationService.preReserve(room.get(),
                filter.getCheckIn(checkInHour, checkInMinute),
                filter.getCheckOut(checkOutHour, checkOutMinute));

        model.addAttribute("reservation", new ReservationDto(reservation));
        return "reservation";
    }

    @PostMapping("/reservation/confirm")
    public String confirmReservation(Model model,
                                     @Valid @ModelAttribute ReservationDto reservation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("reservation", reservation);
            return "reservation";
        }

        Optional<Reservation> reserveOptional = reservationRepository.findById(reservation.getId());
        if (!reserveOptional.isPresent())
            throw new RuntimeException("Reservation isAbsent");

        User user = new User();
        user.setFullName(reservation.getUserFullName());
        user.setPhone(reservation.getUserPhone());
        user.setEmail(reservation.getUserEmail());

        Reservation reserve = reserveOptional.get();
        reserve.setUser(userRepository.save(user));
        reserve.setGuestRemark(reservation.getGuestRemark());
        reservationRepository.save(reserve);

        model.addAttribute("message", "message.reservation-confirmed");
        return "info";
    }

    @PostMapping("/reservation/reject")
    public String rejectReservation(@ModelAttribute ReservationDto reservation, Model model) {
        reservationRepository.deleteById(reservation.getId());
        model.addAttribute("message", "message.reservation-rejected");
        return "info";
    }
}

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class AvailabilityFilterDto {

    String checkInCheckOut;

    Integer persons;

    boolean isSameDay() {
        String[] inOut = checkInCheckOut.split(" - ");
        return inOut[0].equals(inOut[1]);
    }

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

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
class ReservationDto {

    Long id;

    @NotEmpty
    String userFullName;

    @Email
    @NotEmpty
    String userEmail;

    @NotEmpty
    String userPhone;

    String roomNumber;

    LocalDateTime checkIn, checkOut;

    BigDecimal totalCost;

    String guestRemark;

    ReservationDto() {
    }

    ReservationDto(Reservation reservation) {
        id = reservation.getId();
        roomNumber = reservation.getRoom().getNumber();
        checkIn = reservation.getCheckIn();
        checkOut = reservation.getCheckOut();
        totalCost = reservation.getTotalCost();
    }
}
