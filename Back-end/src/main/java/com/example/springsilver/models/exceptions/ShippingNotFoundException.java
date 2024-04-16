package com.example.springsilver.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class ShippingNotFoundException extends RuntimeException{

    public ShippingNotFoundException(Long id) {
        super(String.format("Shipping with id %d was not found!",id));
    }
}
