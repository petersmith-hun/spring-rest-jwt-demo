package hu.psprog.dev.jwtdemo.web.exception;

public class InvalidJWTTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidJWTTokenException(String message) {
        super(message);
    }
    
    public InvalidJWTTokenException(Exception exc) {
        super(exc);
    }
}
