package app.VilaExplorer.exception;

public class TipoPlatoNotFoundException extends Exception {
    public TipoPlatoNotFoundException() {
    }

    public TipoPlatoNotFoundException(String message) {
        super(message);
    }

    public TipoPlatoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TipoPlatoNotFoundException(Throwable cause) {
        super(cause);
    }

    public TipoPlatoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
