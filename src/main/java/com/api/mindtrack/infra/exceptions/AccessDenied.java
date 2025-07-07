package com.api.mindtrack.infra.exceptions;

public class AccessDenied extends RuntimeException {
    public AccessDenied(String message) {
        super(message);
    }

    public AccessDenied() {
      super("Acesso Negado.");
    }
}
