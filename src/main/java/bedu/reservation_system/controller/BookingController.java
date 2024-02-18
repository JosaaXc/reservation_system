package bedu.reservation_system.controller;

import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.models.AvailableTables;
import bedu.reservation_system.service.BookingService;
import bedu.reservation_system.service.AvailableTablesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final AvailableTablesService availableTablesService;

    public BookingController(BookingService bookingService, AvailableTablesService availableTablesService) {
        this.bookingService = bookingService;
        this.availableTablesService = availableTablesService;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Bookings booking) {
        try {
            AvailableTables table = availableTablesService.findByNumTable(booking.getNumTable());
            if (table != null && table.isOccupied()) {
                return new ResponseEntity<>("Table is already occupied", HttpStatus.BAD_REQUEST);
            }

            ResponseEntity<?> response = bookingService.create(booking);  // Aquí creamos una nueva reserva, generamos un nuevo token y enviamos un mensaje
            return response;
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{ticket}")
    public ResponseEntity<?> updateBooking(@PathVariable("ticket") String ticket, @RequestBody Bookings newBooking) {
        try {
            Bookings existingBooking = bookingService.findByTicket(ticket);
            if (existingBooking == null || !existingBooking.isValidated()) {
                return new ResponseEntity<>("Booking not validated or does not exist", HttpStatus.BAD_REQUEST);
            }

            if (existingBooking.getNumTable() != newBooking.getNumTable()) {
                AvailableTables table = availableTablesService.findByNumTable(newBooking.getNumTable());
                if (table != null && table.isOccupied()) {
                    return new ResponseEntity<>("Table is already occupied", HttpStatus.BAD_REQUEST);
                }
            }

            // Update the fields
            if (newBooking.getName() != null)
                existingBooking.setName(newBooking.getName());
            if (newBooking.getDate() != null)
                existingBooking.setDate(newBooking.getDate());
            if (newBooking.getTime() != null)
                existingBooking.setTime(newBooking.getTime());
            if (newBooking.getNumberPeople() >= 0)
                existingBooking.setNumberPeople(newBooking.getNumberPeople());
            if (newBooking.getNumTable() >= 0)
                existingBooking.setNumTable(newBooking.getNumTable());
            if (newBooking.getPhoneNumber() != null)
                existingBooking.setPhoneNumber(newBooking.getPhoneNumber());

            Bookings updatedBooking = bookingService.update(existingBooking);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}