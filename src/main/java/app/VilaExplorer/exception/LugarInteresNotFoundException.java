package app.VilaExplorer.exception;

public class LugarInteresNotFoundException extends Exception {
    public LugarInteresNotFoundException() {
    }

    public LugarInteresNotFoundException(String message) {
        super(message);
    }

    public LugarInteresNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LugarInteresNotFoundException(Throwable cause) {
        super(cause);
    }

    public LugarInteresNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
