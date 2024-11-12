package app.VilaExplorer.exception;

public class ArticuloNotFoundException extends Throwable {
    public ArticuloNotFoundException() {
    }

    public ArticuloNotFoundException(String s) {
    }

    public ArticuloNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticuloNotFoundException(Throwable cause) {
        super(cause);
    }

    public ArticuloNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
