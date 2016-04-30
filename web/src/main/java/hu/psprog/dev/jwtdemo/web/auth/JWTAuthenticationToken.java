package hu.psprog.dev.jwtdemo.web.auth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import hu.psprog.dev.jwtdemo.web.util.JWTUtility;

/**
 * Wrapped JWT Authentication token. Shall be validated by {@link JWTAuthenticationProvider}. 
 */
public class JWTAuthenticationToken implements Authentication {
    
    private static final long serialVersionUID = 1L;
    private static final String JWT_AUTH_NAME = "JWT Authentication";

    private JWTPayload payload;
    private boolean authenticated = false;

    public JWTAuthenticationToken() {
        // Serializable
    }

    public JWTAuthenticationToken(String token) {
        
        this.payload = JWTUtility.decode(token);
    }

    @Override
    public String getName() {

        return JWT_AUTH_NAME;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(payload.getRole().toString());
        
        return Arrays.asList(new GrantedAuthority[] { authority });
    }

    @Override
    public Object getCredentials() {

        return payload.getHashedPassword();
    }

    @Override
    public Object getDetails() {

        return payload;
    }

    @Override
    public Object getPrincipal() {

        return payload.getUsername();
    }

    @Override
    public boolean isAuthenticated() {

        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        this.authenticated = isAuthenticated;
    }
}
