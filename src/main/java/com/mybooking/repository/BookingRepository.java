package com.mybooking.repository;

import com.mybooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.property.id = :propertyId AND " +
            "(b.startDate < :endDate AND b.endDate > :startDate) AND b.status != 'CANCELED'")
    List<Booking> findOverlappingBookings(LocalDate startDate, LocalDate endDate, Long propertyId);

    @Query("SELECT b FROM Booking b WHERE b.property.id = :propertyId AND " +
            "(b.startDate < :endDate AND b.endDate > :startDate) AND b.status != 'CANCELED' AND b.id != :idBooking")
    List<Booking> findOverlappingOtherBookings(LocalDate startDate, LocalDate endDate, Long propertyId, Long idBooking);

}
