package hu.psprog.dev.jwtdemo.web.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Authentication provider for JWT based authentication.
 */
public class JWTAuthenticationProvider implements AuthenticationProvider {
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        // provider does not have to do anything, but setting token to authenticated status
        
        authentication.setAuthenticated(true);

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
