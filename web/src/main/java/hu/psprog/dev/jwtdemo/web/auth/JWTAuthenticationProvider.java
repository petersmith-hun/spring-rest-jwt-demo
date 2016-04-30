package hu.psprog.dev.jwtdemo.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import hu.psprog.dev.jwtdemo.web.exception.InvalidJWTTokenException;

/**
 * Authentication provider for JWT based authentication.
 */
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private static final String EXCEPTION_ROLE = "Retrieved user does not have specified role.";
    private static final String EXCEPTION_PASSWORD = "Password of retrieved user is not the same as the stored one";
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        validateTokenByUserDetails(userDetails, authentication);
        authentication.setAuthenticated(true);

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private void validateTokenByUserDetails(UserDetails userDetails, Authentication authentication) {
        
        if(!userDetails.getPassword().equals(authentication.getCredentials())) {
            
            throw new InvalidJWTTokenException(EXCEPTION_PASSWORD);
        }
        
        if(!userDetails.getAuthorities().containsAll(authentication.getAuthorities())) {
            
            throw new InvalidJWTTokenException(EXCEPTION_ROLE);
        }
    }
}
