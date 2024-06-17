package com.mybooking.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.mybooking.model.Booking;
import com.mybooking.model.Guest;
import com.mybooking.model.Property;
import com.mybooking.enums.StatusBooking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    private Booking booking;
    private Property property;
    private Guest guest;
/*
    @BeforeEach
    void setUp() {
        guest = Guest.builder().name("John Doe").build();
        property = Property.builder().name("Sample Property").build();
        booking = Booking.builder()
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status(StatusBooking.RESERVADO)
                .details("Test Booking")
                .guest(guest)
                .property(property)
                .build();
        bookingRepository.save(booking);
    }

    @Test
    void testFindOverlappingBookings() {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), property.getId());
        assertFalse(overlappingBookings.isEmpty());
    }

    @Test
    void testSaveBooking() {
        Booking savedBooking = bookingRepository.save(booking);
        assertNotNull(savedBooking);
        assertNotNull(savedBooking.getId());
    }*/
}
