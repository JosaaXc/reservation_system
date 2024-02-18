package bedu.reservation_system.controller;

import bedu.reservation_system.models.Bookings;
import bedu.reservation_system.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @SuppressWarnings("null")
    @Test
    public void createBookingTest() throws Exception {
        Bookings booking = new Bookings();

        doReturn(ResponseEntity.ok(booking)).when(bookingService).create(booking);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Josafat2\",\"date\":\"2022-12-31\",\"time\":\"10:35\",\"numberPeople\":4,\"numTable\":1,\"phoneNumber\":\"9711082740\"}")) // Replace with JSON representation of the booking object
                .andExpect(status().isOk());
    }
}