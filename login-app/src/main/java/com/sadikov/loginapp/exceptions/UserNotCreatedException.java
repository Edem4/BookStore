package com.sadikov.loginapp.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserNotCreatedException extends Exception{
    private HttpStatus status;
    public UserNotCreatedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}