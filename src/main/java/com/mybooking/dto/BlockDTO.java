package com.mybooking.dto;

import com.mybooking.model.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlockDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private Property property;

}
