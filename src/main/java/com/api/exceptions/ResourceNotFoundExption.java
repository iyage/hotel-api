package com.api.exceptions;

public class ResourceNotFoundExption extends RuntimeException{
    public ResourceNotFoundExption(String message) {
        super(message);
    }
}
