package com.bytegatherer.jsonplusyaml.exceptions;

public class NotYamlException extends RuntimeException {

    public NotYamlException(String message) {
        super(message);
    }

    public NotYamlException(String message, Throwable cause) {
        super(message, cause);
    }
}
