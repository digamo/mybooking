package com.mybooking.service;

import static org.junit.jupiter.api.Assertions.*;

import com.mybooking.model.Booking;
import com.mybooking.model.Guest;
import com.mybooking.model.Property;
import com.mybooking.repository.BlockRepository;
import com.mybooking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;
    private Property property;
    private Guest guest;

/*    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        guest = Guest.builder().id(1L).name("John Doe").build();
        property = Property.builder().id(1L).name("Sample Property").build();
        booking = Booking.builder()
                .id(1L)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status(StatusBooking.RESERVADO)
                .details("Test Booking")
                .guest(guest)
                .property(property)
                .build();
    }

    @Test
    void testCreateBooking() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(booking);

        assertNotNull(createdBooking);
        assertEquals(StatusBooking.RESERVADO, createdBooking.getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testUpdateBooking() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Optional<Booking> updatedBooking = bookingService.updateBooking(1L, booking);

        assertTrue(updatedBooking.isPresent());
        assertEquals(booking.getId(), updatedBooking.get().getId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCancelBooking() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(1L);

        assertEquals(StatusBooking.CANCELADO, booking.getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testRescheduleBooking() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Optional<Booking> rescheduledBooking = bookingService.rescheduleBooking(1L, booking);

        assertTrue(rescheduledBooking.isPresent());
        assertEquals(StatusBooking.REMARCADO, rescheduledBooking.get().getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testDeleteBooking() {
        bookingService.deleteBooking(1L);
        verify(bookingRepository, times(1)).deleteById(anyLong());
    }*/
}
