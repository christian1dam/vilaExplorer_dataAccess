package app.VilaExplorer.exception;

public class PlatoNotFoundException extends Exception{
    public PlatoNotFoundException() {
    }

    public PlatoNotFoundException(String message) {
        super(message);
    }

    public PlatoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatoNotFoundException(Throwable cause) {
        super(cause);
    }

    public PlatoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
