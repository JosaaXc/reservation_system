package bedu.reservation_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import bedu.reservation_system.models.Tickets;
import bedu.reservation_system.models.AvailableTables;
import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.models.Tokens;
import bedu.reservation_system.service.AvailableTablesService;
import bedu.reservation_system.service.BookingService;
import bedu.reservation_system.service.TokenService;
import bedu.reservation_system.service.TicketService;

@RestController
@RequestMapping("/cancel-booking")
public class CancelBookingController {

    private final BookingService bookingService;
    private final AvailableTablesService availableTablesService;
    private final TokenService tokenService;
    private final TicketService ticketService;

    public CancelBookingController(BookingService bookingService, AvailableTablesService availableTablesService, TokenService tokenService, TicketService ticketService) {
        this.bookingService = bookingService;
        this.availableTablesService = availableTablesService;
        this.tokenService = tokenService;
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<String> cancelBooking(@RequestParam("ticket") String ticket) {
        Bookings booking = bookingService.findByTicket(ticket);
        if (booking == null || !booking.isValidated()) {
            return new ResponseEntity<>("Ticket not validated or does not exist", HttpStatus.BAD_REQUEST);
        }

        int numTable = booking.getNumTable();
        AvailableTables table = availableTablesService.findByNumTable(numTable);
        if (table == null) {
            return new ResponseEntity<>("Table not found", HttpStatus.NOT_FOUND);
        }

        table.setOccupied(false);
        availableTablesService.save(table);

        Tokens bookingToken = booking.getToken();
        Tickets bookingTicket = ticketService.findByBooking(booking);

        // Delete the booking before deleting the token
        bookingService.delete(booking);

        if (bookingToken != null) {
            tokenService.delete(bookingToken);
        }

        if (bookingTicket != null) {
            ticketService.delete(bookingTicket);
        }

        return new ResponseEntity<>("Booking cancelled", HttpStatus.OK);
    }
}