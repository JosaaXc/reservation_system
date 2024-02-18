package bedu.reservation_system.service;

import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.models.Tokens;
import bedu.reservation_system.models.Tickets;
import bedu.reservation_system.repository.BookingRepository;
import bedu.reservation_system.repository.TokenRepository;
import org.springframework.transaction.annotation.Transactional;
import bedu.reservation_system.repository.TicketRepository;

import org.hibernate.mapping.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Random;
import okhttp3.*;
import java.io.IOException;
import java.util.HashMap;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TokenRepository tokenRepository;
    private final TicketRepository ticketRepository;
    String apiKey = System.getenv("INFOBIP_API_KEY");

    public BookingService(BookingRepository bookingRepository, TokenRepository tokenRepository, TicketRepository ticketRepository) {
        this.bookingRepository = bookingRepository;
        this.tokenRepository = tokenRepository;
        this.ticketRepository = ticketRepository;
    }

    public Bookings findByTicket(String ticket) {
        return bookingRepository.findByTicketTicket(ticket);
    }

    @Transactional
    public ResponseEntity<?> create(Bookings booking) throws Exception {
        int tokenNumber = new Random().nextInt(9000) + 1000;
        Tokens token = new Tokens();
        token.setToken(tokenNumber);

        String ticketNumber = generateUniqueTicket();
        Tickets ticket = new Tickets();
        ticket.setTicket(ticketNumber);

        booking.setToken(token);
        booking.setTicket(ticket);

        if (sendTokenByMessage(booking, token)) {
            token = tokenRepository.save(token);
            ticket = ticketRepository.save(ticket);
            booking = bookingRepository.save(booking);
            
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.badRequest().body("Failed to send token by message");
        }
    }

    public Bookings update(Bookings booking) {
        return bookingRepository.save(booking);
    }

    private String generateUniqueTicket() {
        String ticket = UUID.randomUUID().toString();
        ticket = ticket.replace("-", "");
        ticket = ticket.substring(0, 10);
        return ticket;
    }

    private boolean sendTokenByMessage(Bookings booking, Tokens token) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = String.format("{\"messages\":[{\"destinations\":[{\"to\":\"52%s\"}],\"from\":\"ServiceSMS\",\"text\":\"Tu token de confirmación de reserva es: %d\"}]}", booking.getPhoneNumber(), token.getToken());
        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
            .url("https://n8vzwj.api.infobip.com/sms/2/text/advanced") // Reemplaza con tu URL base de API si es diferente
            .method("POST", body)
            .addHeader("Authorization", "App " + apiKey)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("Response code: " + response.code());
            System.out.println("Response message: " + response.message());
            if (response.body() != null) {
                System.out.println("Response body: " + response.body().string());
            }
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}