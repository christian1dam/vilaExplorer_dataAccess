package app.VilaExplorer.exception;

public class CategoriaPlatoNotFoundException extends Exception {
    public CategoriaPlatoNotFoundException() {
    }

    public CategoriaPlatoNotFoundException(String message) {
        super(message);
    }

    public CategoriaPlatoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoriaPlatoNotFoundException(Throwable cause) {
        super(cause);
    }

    public CategoriaPlatoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
