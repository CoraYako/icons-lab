package com.icons.exception;

public class ParamNotFoundException extends RuntimeException {
    public ParamNotFoundException(String error) {
        super(error);
    }
}