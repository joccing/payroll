package com.example.payroll.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataRetrievalException extends Exception {

    public DataRetrievalException(String errorMessage) {
        super(errorMessage);
    }
}
