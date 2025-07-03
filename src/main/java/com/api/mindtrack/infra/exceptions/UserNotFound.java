package com.api.mindtrack.infra.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super(message);
    }
    public UserNotFound() {
      super("User Not Found.");
    }
}
