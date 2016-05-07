package hu.psprog.dev.jwtdemo.service;

import java.util.Arrays;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        
        if(!username.equals("test")) {
            throw new UsernameNotFoundException("Username not found");
        }
        
        return new User("test", "1234", Arrays.asList(new GrantedAuthority[] {authority}));
    }

}
