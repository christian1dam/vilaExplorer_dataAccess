package app.VilaExplorer.exception;

public class CoordenadasNotFoundException extends Exception {
    public CoordenadasNotFoundException() {
    }

    public CoordenadasNotFoundException(String s) {
    }

    public CoordenadasNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoordenadasNotFoundException(Throwable cause) {
        super(cause);
    }

    public CoordenadasNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
