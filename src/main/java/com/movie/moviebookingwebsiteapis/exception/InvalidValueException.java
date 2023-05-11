package com.movie.moviebookingwebsiteapis.exception;

public class InvalidValueException extends Exception{

    public InvalidValueException(){
        super();
    }

    public InvalidValueException(String message){
        super(message);
    }
}
