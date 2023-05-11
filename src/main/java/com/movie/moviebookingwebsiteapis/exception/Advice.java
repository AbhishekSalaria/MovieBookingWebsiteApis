package com.movie.moviebookingwebsiteapis.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Advice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoElementFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody  ExceptionResponse handleNoElementFoundException(NoElementFoundException exception, HttpServletRequest request) {

        ExceptionResponse response = new ExceptionResponse();
        response.setUrl(request.getRequestURI());
        response.setMessage(exception.getMessage());
        return response;
    }

    @ExceptionHandler(InvalidValueException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody  ExceptionResponse handleInvalidValueException(InvalidValueException exception, HttpServletRequest request) {

        ExceptionResponse response = new ExceptionResponse();
        response.setUrl(request.getRequestURI());
        response.setMessage(exception.getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody  ExceptionResponse handleException(Exception exception, HttpServletRequest request) {

        ExceptionResponse response = new ExceptionResponse();
        response.setUrl(request.getRequestURI());
        response.setMessage(exception.getMessage());
        return response;
    }
}
