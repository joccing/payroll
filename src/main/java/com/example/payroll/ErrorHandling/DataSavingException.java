package com.example.payroll.ErrorHandling;

public class DataSavingException extends RuntimeException {

    public DataSavingException(String errorMessage) { super(errorMessage);}
}
