package app.VilaExplorer.exception;

public class RolNotFoundException extends Exception {
    public RolNotFoundException() {
    }

    public RolNotFoundException(String message) {
        super(message);
    }

    public RolNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RolNotFoundException(Throwable cause) {
        super(cause);
    }

    public RolNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
