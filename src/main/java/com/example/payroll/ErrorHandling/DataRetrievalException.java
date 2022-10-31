package com.example.payroll.ErrorHandling;

public class DataRetrievalException extends Exception {
    public DataRetrievalException(String errorMessage) {
        super(errorMessage);
    }
}
