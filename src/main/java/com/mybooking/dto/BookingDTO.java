package com.mybooking.dto;

import com.mybooking.enums.StatusBooking;
import com.mybooking.model.Guest;
import com.mybooking.model.Property;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
