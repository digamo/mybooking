package com.mybooking.service;

import com.mybooking.dto.BookingDTO;
import com.mybooking.enums.StatusBooking;
import com.mybooking.exception.BookingException;
import com.mybooking.model.Booking;
import com.mybooking.repository.BlockRepository;
import com.mybooking.repository.BookingRepository;
import com.mybooking.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BlockRepository blockRepository;

    public Booking getBooking(Long id) {
        return findById(id);
    }

    public Booking createBooking(BookingDTO bookingDTO) {
        if (isBookingOverlap(bookingDTO)) {
            throw new BookingException(Constants.BOOKING_DATES_OVERLAP, HttpStatus.BAD_REQUEST);
        }
        Booking newBooking = new Booking();
        newBooking.setStartDate(bookingDTO.getStartDate());
        newBooking.setEndDate(bookingDTO.getEndDate());
        newBooking.setStatus(StatusBooking.BOOKED);
        newBooking.setDetails(bookingDTO.getDetails());
        newBooking.setGuest(bookingDTO.getGuest());
        newBooking.setProperty(bookingDTO.getProperty());

        return bookingRepository.save(newBooking);
    }

    public Booking updateBooking(Long id, BookingDTO bookingDTO) {
        if (isBookingOverlap(bookingDTO)) {
            throw new BookingException(Constants.BOOKING_DATES_OVERLAP, HttpStatus.BAD_REQUEST);
        }

        Booking updatedBooking = findById(id);
        updatedBooking.setStartDate(bookingDTO.getStartDate());
        updatedBooking.setEndDate(bookingDTO.getEndDate());
        updatedBooking.setDetails(bookingDTO.getDetails());

        return bookingRepository.save(updatedBooking);
    }

    public void cancelBooking(Long id) {
        Booking cancelledBooking = findById(id);
        cancelledBooking.setStatus(StatusBooking.CANCELED);
        bookingRepository.save(cancelledBooking);
    }

    public Booking rescheduleBooking(Long id, BookingDTO bookingDTO) {
        Booking rescheduledBooking = findById(id);
        if(!StatusBooking.CANCELED.equals(bookingDTO.getStatus())) {
            throw new BookingException("Booking is not canceled to do a rebook", HttpStatus.BAD_REQUEST);
        }

        if (isBookingOverlap(bookingDTO)) {
            throw new BookingException(Constants.BOOKING_DATES_OVERLAP, HttpStatus.BAD_REQUEST);
        }

        rescheduledBooking.setStartDate(bookingDTO.getStartDate());
        rescheduledBooking.setEndDate(bookingDTO.getEndDate());
        rescheduledBooking.setDetails(bookingDTO.getDetails());
        rescheduledBooking.setStatus(StatusBooking.RESCHEDULED);

        return bookingRepository.save(rescheduledBooking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    private boolean isBookingOverlap(BookingDTO bookingDTO) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId());
        if (!overlappingBookings.isEmpty()) {
            return true;
        }
        return !blockRepository.findOverlappingBlocks(
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId()).isEmpty();
    }

    private Booking findById(Long id){
        return bookingRepository.findById(id).orElseThrow(() -> new BookingException("Block not found", HttpStatus.NOT_FOUND));
    }
}
