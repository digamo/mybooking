package com.mybooking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookingException extends RuntimeException {

    private HttpStatus status;
    public BookingException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
