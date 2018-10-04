package ua.olezha.hotel.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/management")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManagementController {

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

    @GetMapping("/users")
    public String users() {
        return "management-users";
    }
}
