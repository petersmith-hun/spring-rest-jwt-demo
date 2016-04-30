package hu.psprog.dev.jwtdemo.web.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hu.psprog.dev.jwtdemo.web.auth.JWTAuthenticationFilter;
import hu.psprog.dev.jwtdemo.web.auth.JWTAuthenticationProvider;
import hu.psprog.dev.jwtdemo.web.auth.JWTUserDetailsService;
import hu.psprog.dev.jwtdemo.web.auth.RestAuthenticationEntryPoint;
import hu.psprog.dev.jwtdemo.web.auth.RestAuthenticationSuccessHandler;
import hu.psprog.dev.jwtdemo.web.util.JWTUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:jwtconfig.properties")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String URL_REGISTER = "/api/user/register";
    private static final String URL_LOGIN = "/api/user/login";
    
    // TODO password hasher
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.issuer}")
    private String jwtIssuer;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer securityProperties() {
    	return new PropertySourcesPlaceholderConfigurer();
    }
    
    @PostConstruct
    public void setupJWT() {
        JWTUtility.setIssuer(jwtIssuer);
        JWTUtility.setSecret(jwtSecret);
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
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        
        return new RestAuthenticationEntryPoint();
    }
    
    @Bean
    public AuthenticationSuccessHandler restAuthenticationSuccessHandler() {
        
        return new RestAuthenticationSuccessHandler();
    }
    
    @Bean
    public UserDetailsService jwtUserDetailsService() {
    	
    	return new JWTUserDetailsService();
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
            .userDetailsService(jwtUserDetailsService());
    }
    
    
}
