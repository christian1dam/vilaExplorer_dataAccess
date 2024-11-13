package app.VilaExplorer.exception;

public class LugarInteresNotActiveException extends Exception {
    public LugarInteresNotActiveException() {
    }

    public LugarInteresNotActiveException(String message) {
        super(message);
    }

    public LugarInteresNotActiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public LugarInteresNotActiveException(Throwable cause) {
        super(cause);
    }

    public LugarInteresNotActiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
