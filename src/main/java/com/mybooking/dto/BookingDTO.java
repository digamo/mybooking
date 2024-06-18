package com.mybooking.dto;

import com.mybooking.enums.StatusBooking;
import com.mybooking.model.Guest;
import com.mybooking.model.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    @Setter
    private LocalDate startDate;
    @Setter
    private LocalDate endDate;
    private StatusBooking status;
    private String details;
    private Guest guest;
    @Setter
    private Property property;

}
