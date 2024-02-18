package bedu.reservation_system.controller;

import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Bookings booking) {
        try {
            ResponseEntity<?> response = bookingService.create(booking);  // Aquí creamos una nueva reserva, generamos un nuevo token y enviamos un mensaje
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}