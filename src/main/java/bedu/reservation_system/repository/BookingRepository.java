package bedu.reservation_system.repository;

import bedu.reservation_system.models.Bookings;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Bookings, Integer> {
    Bookings findByTicketTicket(String ticket);
    Bookings findByDateAndTimeAndValidated(LocalDate date, LocalTime time, boolean validated);
}