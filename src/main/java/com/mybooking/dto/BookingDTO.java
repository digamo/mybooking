package com.mybooking.dto;

import com.mybooking.enums.StatusBooking;
import com.mybooking.model.Guest;
import com.mybooking.model.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private StatusBooking status;
    private String details;
    private Guest guest;
    private Property property;

}
