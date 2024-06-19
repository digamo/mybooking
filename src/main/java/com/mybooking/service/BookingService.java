package com.mybooking.service;

import com.mybooking.dto.BookingDTO;
import com.mybooking.enums.StatusBooking;
import com.mybooking.exception.BookingException;
import com.mybooking.exception.Constants;
import com.mybooking.model.Block;
import com.mybooking.model.Booking;
import com.mybooking.repository.BlockRepository;
import com.mybooking.repository.BookingRepository;
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
        return this.findById(id);
    }

    public Booking createBooking(BookingDTO bookingDTO) {
        validBooking(bookingDTO, null);

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
        validBooking(bookingDTO, id);

        Booking updatedBooking = findById(id);
        updatedBooking.setStartDate(bookingDTO.getStartDate());
        updatedBooking.setEndDate(bookingDTO.getEndDate());
        updatedBooking.setDetails(bookingDTO.getDetails());

        return bookingRepository.save(updatedBooking);
    }

    public Booking cancelBooking(Long id) {
        Booking cancelledBooking = findById(id);
        cancelledBooking.setStatus(StatusBooking.CANCELED);
        return bookingRepository.save(cancelledBooking);
    }

    public Booking rescheduleBooking(Long id) {
        Booking rescheduledBooking = findById(id);
        if(!StatusBooking.CANCELED.equals(rescheduledBooking.getStatus())) {
            throw new BookingException(Constants.BOOKING_NOT_CANCELED, HttpStatus.BAD_REQUEST);
        }
        rescheduledBooking.setStatus(StatusBooking.BOOKED);

        return bookingRepository.save(rescheduledBooking);
    }

    public void deleteBooking(Long id) {
        Booking deletedBooking = findById(id);
        bookingRepository.deleteById(deletedBooking.getId());
    }

    public List<Booking> findBookings(BookingDTO bookingDTO) {
        return bookingRepository.findOverlappingBookings(
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId());
    }

    public List<Booking> findOtherBookings(BookingDTO bookingDTO, Long idBooking) {
        return bookingRepository.findOverlappingOtherBookings(
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId(), idBooking);
    }

    private Booking findById(Long id){
        return bookingRepository.findById(id).orElseThrow(() -> new BookingException(Constants.BOOKING_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void validBooking(BookingDTO bookingDTO, Long idBooking) {

        List<Block> blocks = blockRepository.findOverlappingBlocks(
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId());

        List<Booking> bookings;
        if(idBooking != null)
            bookings = findOtherBookings(bookingDTO, idBooking);
        else
            bookings = findBookings(bookingDTO);

        if (!blocks.isEmpty() || !bookings.isEmpty()) {
            throw new BookingException(Constants.BOOKING_DATES_OVERLAP, HttpStatus.BAD_REQUEST);
        }
    }
}
