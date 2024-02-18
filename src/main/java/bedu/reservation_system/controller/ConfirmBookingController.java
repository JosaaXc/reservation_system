package bedu.reservation_system.controller;

import org.springframework.web.bind.annotation.RestController;

import bedu.reservation_system.dto.TokenBody;
import bedu.reservation_system.models.AvailableTables;
import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.models.Tokens;
import bedu.reservation_system.service.AvailableTablesService;
import bedu.reservation_system.service.BookingService;
import bedu.reservation_system.service.TokenService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/confirm-booking")
public class ConfirmBookingController {

    private final BookingService bookingService;
    private final AvailableTablesService availableTablesService;
    private final TokenService tokenService;

    public ConfirmBookingController(BookingService bookingService, AvailableTablesService availableTablesService, TokenService tokenService) {
        this.bookingService = bookingService;
        this.availableTablesService = availableTablesService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public String confirmBooking(@RequestParam("ticket") String ticket, @RequestBody TokenBody tokenBody) {
        int token = tokenBody.getToken();
        Bookings booking = bookingService.findByTicket(ticket);
        if (booking == null) {
            return "Booking not found";
        }

        Tokens bookingToken = booking.getToken();
        if (bookingToken == null || bookingToken.getToken() != token) {
            return "Invalid token";
        }

        int numTable = booking.getNumTable();
        AvailableTables table = availableTablesService.findByNumTable(numTable);
        if (table == null) {
            return "Table not found";
        }

        booking.setValidated(true);
        table.setOccupied(true);
        bookingService.update(booking);
        availableTablesService.save(table);

        return "Booking confirmed";
    }

}
