package hu.psprog.dev.jwtdemo.web.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;

import hu.psprog.dev.jwtdemo.web.auth.JWTPayload;
import hu.psprog.dev.jwtdemo.web.auth.Role;
import hu.psprog.dev.jwtdemo.web.exception.InvalidAuthorizationHeaderException;
import hu.psprog.dev.jwtdemo.web.exception.InvalidJWTTokenException;
import hu.psprog.dev.jwtdemo.web.model.JWTAuthenticationAnswerModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT encoder/decoder utility.
 * 
 * Application specific JWT secret key shall be provided in security.properties file. 
 */
public class JWTUtility {

    private static final String JWT_ISSUED_AT = "iat";
    private static final String JWT_EXPIRES = "exp";
    private static final String AUTH_ERROR_HEADER = "Authorization header must be present.";
    private static final String AUTH_ERROR_SCHEMA = "Authorization schema must be 'Bearer'";
    
    private static final String JWT_USERNAME = "usr";
    private static final String JWT_USER_ROLE = "rol";
    
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_BEARER = "Bearer ";
    
    private static final Integer EXPIRES_IN_HOUR = 4;
    
    private static String secret;

    private JWTUtility() {
        // prevent instantiation
    }
    
    public static void setSecret(String secret) {
        JWTUtility.secret = secret;
    }
    
    /**
     * Generates token from {@link UserDetails} object.
     * 
     * @param userDetails {@link UserDetails} object to generate token based on
     * @return token wrapped in {@link JWTAuthenticationAnswerModel} object
     */
    public static JWTAuthenticationAnswerModel generateToken(UserDetails userDetails) {

        String roles = userDetails.getAuthorities().stream()
                .map(e -> e.getAuthority())
                .collect(Collectors.joining(","));
        
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_USERNAME, userDetails.getUsername());
        claims.put(JWT_USER_ROLE, roles);
        claims.put(JWT_EXPIRES, generateExpiration());
        claims.put(JWT_ISSUED_AT, generateIssuedAt());
        
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        
        return new JWTAuthenticationAnswerModel(token);
    }
    
    /**
     * Decodes given JWT token and returns its payload's content as {@link JWTPayload} object.
     * 
     * @param token given (raw) token
     * @return {@link JWTPayload} object on success with the contents of JWT payload section
     * @throws InvalidJWTTokenException 
     */
    public static JWTPayload decode(String token) throws InvalidJWTTokenException {

        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            
            JWTPayload payload = new JWTPayload();
            payload.setUsername(claims.get(JWT_USERNAME, String.class));
            payload.setExpires(claims.getExpiration());
            payload.setIssuedAt(claims.getIssuedAt());
            payload.setRole(Role.valueOf(claims.get(JWT_USER_ROLE, String.class)));
            
            return payload;
            
        } catch(Exception exc) {
            throw new InvalidJWTTokenException(exc);
        }
    }
    
    /**
     * Extracts token from servlet request. Requires Bearer type Authorization header.
     * If Authorization and/or Bearer not found, {@link InvalidAuthorizationHeaderException} will be thrown.
     * 
     * @param request standard {@link HttpServletRequest}
     * @return on success, extracted token will be returned as string
     * @throws InvalidAuthorizationHeaderException 
     */
    public static String extractToken(HttpServletRequest request) throws InvalidAuthorizationHeaderException {

        String authHeader = request.getHeader(AUTH_HEADER);

        if(authHeader == null) {
            throw new InvalidAuthorizationHeaderException(AUTH_ERROR_HEADER);
        }
        
        if (!authHeader.startsWith(AUTH_BEARER)) {
            throw new InvalidAuthorizationHeaderException(AUTH_ERROR_SCHEMA);
        }

        String token = authHeader.substring(AUTH_BEARER.length());

        return token;
    }
    
    /**
     * Generates Issued At ("iat") value.
     * 
     * @return {@link Long} timestamp of current date in seconds
     */
    private static Long generateIssuedAt() {
        
        Date currentDate = new Date();
        long timestamp = currentDate.getTime() / 1000;
        
        return timestamp;
    }

    /**
     * Generates Expires ("exp") value.
     * 
     * @return {@link Long} timestamp of expiration date in seconds
     */
    private static Long generateExpiration() {
        
        long timestamp = generateIssuedAt();
        timestamp += (60 * 60 * EXPIRES_IN_HOUR);
        
        return timestamp;
    }
}
