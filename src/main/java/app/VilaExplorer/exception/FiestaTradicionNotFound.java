package app.VilaExplorer.exception;

public class FiestaTradicionNotFound extends Exception {
    public FiestaTradicionNotFound() {
    }

    public FiestaTradicionNotFound(String message) {
        super(message);
    }

    public FiestaTradicionNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public FiestaTradicionNotFound(Throwable cause) {
        super(cause);
    }

    public FiestaTradicionNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
