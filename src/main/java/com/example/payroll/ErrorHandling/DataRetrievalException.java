package com.example.payroll.ErrorHandling;

public class DataRetrievalException extends RuntimeException {

    public DataRetrievalException(String errorMessage) {
        super(errorMessage);
    }
}
