package vsu.task.exeption;

public class EventRepositoryException extends RuntimeException {
    public EventRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
