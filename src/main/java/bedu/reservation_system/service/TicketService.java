package bedu.reservation_system.service;

import org.springframework.stereotype.Service;
import bedu.reservation_system.models.Tickets;
import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.repository.TicketRepository;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Tickets findByBooking(Bookings booking) {
        return ticketRepository.findByBooking(booking);
    }

    public void delete(Tickets ticket) {
        ticketRepository.delete(ticket);
    }
}