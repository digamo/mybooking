package com.mybooking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private String details;

}
