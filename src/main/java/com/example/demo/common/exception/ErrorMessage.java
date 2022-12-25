package com.example.demo.common.exception;

import lombok.Data;

@Data
public class ErrorMessage {

    private final String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
