package com.mybooking.service;

import com.mybooking.dto.BookingDTO;
import com.mybooking.enums.StatusBooking;
import com.mybooking.exception.BookingException;
import com.mybooking.exception.Constants;
import com.mybooking.model.Block;
import com.mybooking.model.Booking;
import com.mybooking.repository.BlockRepository;
import com.mybooking.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BlockRepository blockRepository;

    public Booking getBooking(Long id) {
        log.info("Getting booking with ID: {}", id);
        Booking booking = this.findById(id);
        log.info("Successfully retrieved booking: {}", booking);
        return booking;
    }

    public Booking createBooking(BookingDTO bookingDTO) {
        log.info("Creating booking {}", bookingDTO);

        validBooking(bookingDTO, null);

        Booking newBooking = new Booking();
        newBooking.setStartDate(bookingDTO.getStartDate());
        newBooking.setEndDate(bookingDTO.getEndDate());
        newBooking.setStatus(StatusBooking.BOOKED);
        newBooking.setDetails(bookingDTO.getDetails());
        newBooking.setGuest(bookingDTO.getGuest());
        newBooking.setProperty(bookingDTO.getProperty());

        Booking savedBooking = bookingRepository.save(newBooking);
        log.info("Successfully created booking: {}", savedBooking);
        return savedBooking;
    }

    public Booking updateBooking(Long id, BookingDTO bookingDTO) {
        log.info("Updating Booking {} with ID: {}", bookingDTO, id);

        validBooking(bookingDTO, id);

        Booking updatedBooking = findById(id);
        updatedBooking.setStartDate(bookingDTO.getStartDate());
        updatedBooking.setEndDate(bookingDTO.getEndDate());
        updatedBooking.setDetails(bookingDTO.getDetails());

        Booking savedBooking = bookingRepository.save(updatedBooking);
        log.info("Successfully updated booking: {}", savedBooking);
        return savedBooking;
    }

    public Booking cancelBooking(Long id) {
        log.info("Canceling booking with ID: {}", id);
        Booking cancelledBooking = findById(id);
        cancelledBooking.setStatus(StatusBooking.CANCELED);

        Booking savedBooking = bookingRepository.save(cancelledBooking);
        log.info("Successfully canceled booking: {}", savedBooking);
        return savedBooking;
    }

    public Booking rescheduleBooking(Long id) {
        log.info("Rescheduling booking with ID: {}", id);
        Booking rescheduledBooking = findById(id);
        if(!StatusBooking.CANCELED.equals(rescheduledBooking.getStatus())) {
            log.warn("Attempt to reschedule a booking that is not canceled: {}", rescheduledBooking);
            throw new BookingException(Constants.BOOKING_NOT_CANCELED, HttpStatus.BAD_REQUEST);
        }
        rescheduledBooking.setStatus(StatusBooking.BOOKED);

        Booking savedBooking = bookingRepository.save(rescheduledBooking);
        log.info("Successfully rescheduled booking: {}", savedBooking);
        return savedBooking;
    }

    public void deleteBooking(Long id) {
        log.info("Deleting booking with ID: {}", id);
        Booking deletedBooking = findById(id);
        bookingRepository.deleteById(deletedBooking.getId());
        log.info("Successfully deleted booking with ID: {}", id);
    }

    public List<Booking> findBookings(BookingDTO bookingDTO) {
        log.info("Finding bookings with start date: {} and end date: {} for property: {}",
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId());

        List<Booking> bookings = bookingRepository.findOverlappingBookings(
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId());
        log.info("Found {} bookings", bookings.size());
        return bookings;
    }

    public List<Booking> findOtherBookings(BookingDTO bookingDTO, Long idBooking) {
        log.info("Finding other bookings with start date: {} and end date: {} for property: {} excluding booking ID: {}",
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId(), idBooking);

        List<Booking> bookings = bookingRepository.findOverlappingOtherBookings(
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId(), idBooking);

        log.info("Found {} other bookings", bookings.size());
        return bookings;
    }
    private Booking findById(Long id){
        log.info("Finding booking by ID: {}", id);
        return bookingRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Booking not found with ID: {}", id);
                    return new BookingException(Constants.BOOKING_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
    }
    private void validBooking(BookingDTO bookingDTO, Long idBooking) {
        log.info("Validating booking: {}", bookingDTO);

        List<Block> blocks = blockRepository.findOverlappingBlocks(
                bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.getProperty().getId());

        List<Booking> bookings;
        if(idBooking != null)
            bookings = findOtherBookings(bookingDTO, idBooking);
        else
            bookings = findBookings(bookingDTO);

        if (!blocks.isEmpty() || !bookings.isEmpty()) {
            log.warn("Booking dates overlap for booking: {}", bookingDTO);
            throw new BookingException(Constants.BOOKING_DATES_OVERLAP, HttpStatus.BAD_REQUEST);
        }
        log.info("Booking validated successfully: {}", bookingDTO);
    }
}
