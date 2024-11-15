package com.bytegatherer.jsonplusyaml.exceptions;

public class ConversionFailedException extends RuntimeException {

    public ConversionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
