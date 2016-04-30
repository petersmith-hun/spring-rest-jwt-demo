package hu.psprog.dev.jwtdemo.web.auth;

import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * User details service for JWT based authentication.
 */
public class JWTUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");

        return new User("jwtauth@test.com", "1234", Arrays.asList(new GrantedAuthority[] { authority }));
    }

}
