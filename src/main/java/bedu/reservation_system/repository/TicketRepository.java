package bedu.reservation_system.repository;

import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Integer> {
    Tickets findByBooking(Bookings booking);
}