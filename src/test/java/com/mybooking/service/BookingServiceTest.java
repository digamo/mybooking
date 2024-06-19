package com.mybooking.service;

import com.mybooking.dto.BookingDTO;
import com.mybooking.enums.StatusBooking;
import com.mybooking.exception.BookingException;
import com.mybooking.exception.Constants;
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
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BlockRepository blockRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(LocalDate.of(2024, 6, 10));
        booking.setEndDate(LocalDate.of(2024, 6, 20));

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        Booking foundBooking = bookingService.getBooking(1L);

        assertThat(foundBooking).isNotNull();
        assertThat(foundBooking.getId()).isEqualTo(1L);
    }

    @Test
    void testCreateBooking_Success() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartDate(LocalDate.of(2024, 6, 10));
        bookingDTO.setEndDate(LocalDate.of(2024, 6, 20));
        Property property = new Property();
        property.setId(1L);
        bookingDTO.setProperty(property);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setDetails(bookingDTO.getDetails());
        booking.setGuest(bookingDTO.getGuest());
        booking.setProperty(bookingDTO.getProperty());
        booking.setStatus(StatusBooking.BOOKED);

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(bookingDTO);

        assertThat(createdBooking).isNotNull();
        assertThat(createdBooking.getId()).isEqualTo(1L);
        assertThat(createdBooking.getStatus()).isEqualTo(StatusBooking.BOOKED);
    }

    @Test
    void testCreateBooking_BookingDatesOverlap() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartDate(LocalDate.of(2024, 6, 10));
        bookingDTO.setEndDate(LocalDate.of(2024, 6, 20));
        Property property = new Property();
        property.setId(1L);
        bookingDTO.setProperty(property);

        when(blockRepository.findOverlappingBlocks(any(LocalDate.class), any(LocalDate.class), anyLong()))
                .thenReturn(Collections.emptyList());

        when(bookingRepository.findOverlappingBookings(any(LocalDate.class), any(LocalDate.class), anyLong()))
                .thenReturn(List.of(new Booking()));

        Throwable thrown = catchThrowable(() -> bookingService.createBooking(bookingDTO));

        assertThat(thrown)
                .isInstanceOf(BookingException.class)
                .hasMessage(Constants.BOOKING_DATES_OVERLAP)
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateBooking_Success() {
        Booking existingBooking = new Booking();
        existingBooking.setId(1L);
        existingBooking.setStartDate(LocalDate.of(2024, 6, 10));
        existingBooking.setEndDate(LocalDate.of(2024, 6, 20));
        existingBooking.setDetails("Vacation");

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartDate(LocalDate.of(2024, 6, 15));
        bookingDTO.setEndDate(LocalDate.of(2024, 6, 25));
        Property property = new Property();
        property.setId(1L);
        bookingDTO.setProperty(property);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(existingBooking);

        Booking updatedBooking = bookingService.updateBooking(1L, bookingDTO);

        assertThat(updatedBooking).isNotNull();
        assertThat(updatedBooking.getStartDate()).isEqualTo(LocalDate.of(2024, 6, 15));
        assertThat(updatedBooking.getEndDate()).isEqualTo(LocalDate.of(2024, 6, 25));
    }

    @Test
    void testCancelBooking_Success() {
        Booking existingBooking = new Booking();
        existingBooking.setId(1L);
        existingBooking.setStatus(StatusBooking.BOOKED);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking cancelledBooking = bookingService.cancelBooking(1L);

        assertThat(cancelledBooking).isNotNull();
        assertThat(cancelledBooking.getStatus()).isEqualTo(StatusBooking.CANCELED);
    }

    @Test
    void testRescheduleBooking_Success() {
        Booking cancelledBooking = new Booking();
        cancelledBooking.setId(1L);
        cancelledBooking.setStatus(StatusBooking.CANCELED);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(cancelledBooking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking rescheduledBooking = bookingService.rescheduleBooking(1L);

        assertThat(rescheduledBooking).isNotNull();
        assertThat(rescheduledBooking.getStatus()).isEqualTo(StatusBooking.BOOKED);
    }

    @Test
    void testRescheduleBooking_NotCanceled() {
        Booking bookedBooking = new Booking();
        bookedBooking.setId(1L);
        bookedBooking.setStatus(StatusBooking.BOOKED);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(bookedBooking));

        Throwable thrown = catchThrowable(() -> bookingService.rescheduleBooking(1L));

        assertThat(thrown)
                .isInstanceOf(BookingException.class)
                .hasMessage(Constants.BOOKING_NOT_CANCELED)
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteBooking_Success() {
        Booking existingBooking = new Booking();
        existingBooking.setId(1L);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(existingBooking));

        bookingService.deleteBooking(1L);

        verify(bookingRepository, times(1)).deleteById(existingBooking.getId());
    }
}