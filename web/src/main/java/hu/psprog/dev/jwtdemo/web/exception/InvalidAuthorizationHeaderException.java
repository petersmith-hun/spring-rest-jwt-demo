package hu.psprog.dev.jwtdemo.web.exception;

public class InvalidAuthorizationHeaderException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidAuthorizationHeaderException() {
        super("Invalid or missing Authorization header");
    }
}
