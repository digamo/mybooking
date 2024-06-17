package com.mybooking.controller;

import com.mybooking.dto.BookingDTO;
import com.mybooking.enums.StatusBooking;
import com.mybooking.model.Booking;
import com.mybooking.model.Guest;
import com.mybooking.model.Property;
import com.mybooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;
    private Booking booking;
    private Guest guest;
    private Property property;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

        guest = new Guest();
        guest.setId(1L);
        guest.setName("John Doe");

        property = new Property();
        property.setId(1L);
        property.setName("Sample Property");

        booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(LocalDate.now().plusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(5));
        booking.setStatus(StatusBooking.RESCHEDULED);
        booking.setDetails("Test Booking");
        booking.setGuest(guest);
        booking.setProperty(property);
    }

    @Test
    void testCreateBooking() throws Exception {
        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(booking);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"startDate\": \"2024-06-15\", \"endDate\": \"2024-06-20\", \"details\": \"Test Booking\", \"guest\": {\"id\": 1}, \"property\": {\"id\": 1} }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RESCHEDULED"));
    }

    @Test
    void testUpdateBooking() throws Exception {
        when(bookingService.updateBooking(anyLong(), any(BookingDTO.class))).thenReturn(booking);

        mockMvc.perform(put("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"startDate\": \"2024-06-15\", \"endDate\": \"2024-06-20\", \"details\": \"Test Booking\", \"guest\": {\"id\": 1}, \"property\": {\"id\": 1} }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testCancelBooking() throws Exception {
        mockMvc.perform(put("/bookings/1/cancel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testRescheduleBooking() throws Exception {
        when(bookingService.rescheduleBooking(anyLong(), any(BookingDTO.class))).thenReturn(booking);

        mockMvc.perform(put("/bookings/1/reschedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"startDate\": \"2024-06-15\", \"endDate\": \"2024-06-20\", \"details\": \"Test Booking\", \"guest\": {\"id\": 1}, \"property\": {\"id\": 1} }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteBooking() throws Exception {
        mockMvc.perform(delete("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
