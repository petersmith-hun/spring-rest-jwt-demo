package hu.psprog.dev.jwtdemo.web.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJWTTokenException extends AuthenticationException {

    private static final long serialVersionUID = 1L;
    private static final String EXCEPTION_MESSAGE = "Found issues with provided JWT token while parsing.";
    
    public InvalidJWTTokenException(Exception exception) {
        super(EXCEPTION_MESSAGE, exception);
    }
}
