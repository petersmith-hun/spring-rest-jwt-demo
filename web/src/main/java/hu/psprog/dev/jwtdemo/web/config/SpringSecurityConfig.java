package hu.psprog.dev.jwtdemo.web.config;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hu.psprog.dev.jwtdemo.web.auth.JWTAuthenticationFilter;
import hu.psprog.dev.jwtdemo.web.auth.JWTAuthenticationProvider;
import hu.psprog.dev.jwtdemo.web.auth.RestAuthenticationEntryPoint;
import hu.psprog.dev.jwtdemo.web.auth.RestAuthenticationSuccessHandler;
import hu.psprog.dev.jwtdemo.web.util.JWTUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ENVIRONMENT_VAR_JWT_SECRET = "java:comp/env/jwtSecret";
    private static final String URL_REGISTER = "/api/user/register";
    private static final String URL_LOGIN = "/api/user/login";
    
    @Autowired
    private UserDetailsService userService;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer securityProperties() {
    	return new PropertySourcesPlaceholderConfigurer();
    }
    
    @PostConstruct
    public void setupJWT() throws NamingException {
        
        String jwtSecret = InitialContext.doLookup(ENVIRONMENT_VAR_JWT_SECRET);
        String jwtSecretBase64Encoded = new String(Base64.encode(jwtSecret.getBytes()));
        JWTUtility.setSecret(jwtSecretBase64Encoded);
    }
    
    @Bean
    public AbstractAuthenticationProcessingFilter jwtAuthenticationFilter() throws Exception {
    	
        JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());
        
        return authenticationFilter;
    }
    
    @Bean
    public AuthenticationProvider jwtAuthenticationProvider() {
        
        return new JWTAuthenticationProvider();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationProvider tokenClaimAuthenticationProvider() {
        
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        //provider.setPasswordEncoder(passwordEncoder());
        
        return provider;
    }
    
    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        
        return new RestAuthenticationEntryPoint();
    }
    
    @Bean
    public AuthenticationSuccessHandler restAuthenticationSuccessHandler() {
        
        return new RestAuthenticationSuccessHandler();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
            
            // set URL permissions
            .authorizeRequests()
                .antMatchers(URL_LOGIN).permitAll()
                .antMatchers(URL_REGISTER).permitAll()
                .anyRequest().authenticated()
            .and()
            
            // set processing filter
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                
            // disable CSRF
            .csrf()
                .disable()
                
            // change session management to stateless
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            
            // set entry point
            .exceptionHandling()
            	.authenticationEntryPoint(restAuthenticationEntryPoint())	
            .and()
            
            // enable https connection
            .requiresChannel()
                .anyRequest().requiresSecure();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        auth
            .authenticationProvider(jwtAuthenticationProvider())
            .authenticationProvider(tokenClaimAuthenticationProvider());
    }
    
    
}
