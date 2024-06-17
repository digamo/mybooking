package com.mybooking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlockException extends RuntimeException {
    private HttpStatus status;
    public BlockException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
