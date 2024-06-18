package com.mybooking.controller;

import com.mybooking.dto.BookingDTO;
import com.mybooking.model.Booking;
import com.mybooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<Booking> createBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<Booking> rescheduleBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.rescheduleBooking(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }

}
