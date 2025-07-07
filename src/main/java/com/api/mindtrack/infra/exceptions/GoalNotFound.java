package com.api.mindtrack.infra.exceptions;

public class GoalNotFound extends RuntimeException {
    public GoalNotFound(String message) {
        super(message);
    }

    public GoalNotFound() {
      super("Goal Not Found.");
    }
}
