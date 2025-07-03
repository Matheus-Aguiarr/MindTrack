package com.api.mindtrack.infra.exceptions;

public class SubjectNotFound extends RuntimeException {
    public SubjectNotFound(String message) {
        super(message);
    }

    public SubjectNotFound() {
        super("Subject Not Found");
    }
}
