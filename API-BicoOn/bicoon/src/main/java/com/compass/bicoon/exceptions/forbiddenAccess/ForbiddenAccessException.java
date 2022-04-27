package com.compass.bicoon.exceptions.forbiddenAccess;

public class ForbiddenAccessException extends RuntimeException{

    public ForbiddenAccessException(String msg){
        super(msg);
    }
}
