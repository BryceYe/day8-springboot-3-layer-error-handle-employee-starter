package com.example.demo.controller.advice;

import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.exception.InvalidSalaryEmployeeException;
import com.example.demo.exception.UpdateCompanyException;
import com.example.demo.exception.UpdateEmployeeException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException exceptionHandler(Exception e){
        return new ResponseException(e.getMessage());
    }

    @ExceptionHandler(InvalidAgeEmployeeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException exceptionHandler2(Exception e){
        return new ResponseException(e.getMessage());
    }

    @ExceptionHandler(InvalidSalaryEmployeeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException exceptionHandler3(Exception e){
        return new ResponseException(e.getMessage());
    }

    @ExceptionHandler(UpdateEmployeeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException exceptionHandler4(Exception e){
        return new ResponseException(e.getMessage());
    }

    @ExceptionHandler(UpdateCompanyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException exceptionHandler5(Exception e){
        return new ResponseException(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseException handlerArgumentNotValid(MethodArgumentNotValidException exception){
        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(" | "));
        return new ResponseException(errorMessage);
    }


}
