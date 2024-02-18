package bedu.reservation_system.controller;

import bedu.reservation_system.dto.TokenBody;
import bedu.reservation_system.models.AvailableTables;
import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.models.Tokens;
import bedu.reservation_system.service.AvailableTablesService;
import bedu.reservation_system.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfirmBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private AvailableTablesService availableTablesService;

    @SuppressWarnings("null")
    @Test
    public void confirmBookingTest() throws Exception {
        String ticket = "testTicket";
        int token = 1234;
        TokenBody tokenBody = new TokenBody();
        tokenBody.setToken(token);

        Bookings booking = new Bookings();

        Tokens bookingToken = new Tokens();
        bookingToken.setToken(token);
        booking.setToken(bookingToken); 

        AvailableTables table = new AvailableTables();

        doReturn(booking).when(bookingService).findByTicket(ticket);
        doReturn(table).when(availableTablesService).findByNumTable(booking.getNumTable());

        mockMvc.perform(post("/confirm-booking?ticket=" + ticket)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\":" + token + "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking confirmed"));
    }
}