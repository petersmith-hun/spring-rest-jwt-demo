package hu.psprog.dev.jwtdemo.web.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthorizationHeaderException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public InvalidAuthorizationHeaderException() {
        super("Invalid or missing Authorization header");
    }
    
    public InvalidAuthorizationHeaderException(String message) {
        super(message);
    }    
}
