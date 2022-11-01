package com.example.payroll.ErrorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( value = {RuntimeException.class} )
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleRuntimeException(RuntimeException ex, WebRequest request){
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler( value = {MaxUploadSizeExceededException.class} )
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public ErrorMessage handleMaxUploadSizException(RuntimeException ex, WebRequest request){
        return new ErrorMessage(
                HttpStatus.EXPECTATION_FAILED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }
}
