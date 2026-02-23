package com.kristian.bffagendadortarefas.infractructure.exceptions;

public class ErroException extends RuntimeException {
    public ErroException(String message) {
        super(message);
    }

    public ErroException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
