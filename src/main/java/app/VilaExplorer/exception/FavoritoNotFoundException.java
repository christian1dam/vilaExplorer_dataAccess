package app.VilaExplorer.exception;

public class FavoritoNotFoundException extends Exception {
    public FavoritoNotFoundException() {
    }

    public FavoritoNotFoundException(String message) {
        super(message);
    }

    public FavoritoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FavoritoNotFoundException(Throwable cause) {
        super(cause);
    }

    public FavoritoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
