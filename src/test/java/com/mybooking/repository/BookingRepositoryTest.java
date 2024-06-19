package com.mybooking.repository;

import com.mybooking.enums.StatusBooking;
import com.mybooking.model.Booking;
import com.mybooking.model.Guest;
import com.mybooking.model.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property;
    private Guest guest;

    @BeforeEach
    void setup() {
        guest = new Guest();
        guest.setName("Test Guest");
        guestRepository.save(guest);

        property = new Property();
        property.setName("Test Property");
        propertyRepository.save(property);

        Booking booking1 = new Booking();
        booking1.setStartDate(LocalDate.of(2024, 6, 10));
        booking1.setEndDate(LocalDate.of(2024, 6, 20));
        booking1.setStatus(StatusBooking.BOOKED);
        booking1.setDetails("Trip with my family");
        booking1.setGuest(guest);
        booking1.setProperty(property);

        Booking booking2 = new Booking();
        booking2.setStartDate(LocalDate.of(2024, 7, 10));
        booking2.setEndDate(LocalDate.of(2024, 7, 20));
        booking2.setStatus(StatusBooking.BOOKED);
        booking2.setDetails("Business trip");
        booking2.setGuest(guest);
        booking2.setProperty(property);

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
    }

    @Test
    void testFindOverlappingBookings() {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                LocalDate.of(2024, 6, 15),
                LocalDate.of(2024, 6, 25),
                property.getId());

        assertThat(overlappingBookings).hasSize(1);
        assertThat(overlappingBookings.get(0).getStartDate()).isEqualTo(LocalDate.of(2024, 6, 10));
        assertThat(overlappingBookings.get(0).getEndDate()).isEqualTo(LocalDate.of(2024, 6, 20));
    }

    @Test
    void testFindNonOverlappingBookings() {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                LocalDate.of(2024, 6, 21),
                LocalDate.of(2024, 6, 30),
                property.getId());

        assertThat(overlappingBookings).isEmpty();
    }

    @Test
    void testFindOverlappingOtherBookings() {
        List<Booking> overlappingOtherBookings = bookingRepository.findOverlappingOtherBookings(
                LocalDate.of(2024, 7, 15),
                LocalDate.of(2024, 7, 25),
                property.getId(),
                1L);

        assertThat(overlappingOtherBookings).hasSize(1);
        assertThat(overlappingOtherBookings.get(0).getStartDate()).isEqualTo(LocalDate.of(2024, 7, 10));
        assertThat(overlappingOtherBookings.get(0).getEndDate()).isEqualTo(LocalDate.of(2024, 7, 20));
    }

}
