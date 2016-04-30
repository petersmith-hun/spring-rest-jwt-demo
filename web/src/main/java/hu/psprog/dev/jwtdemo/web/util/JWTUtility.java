package hu.psprog.dev.jwtdemo.web.util;

import javax.servlet.http.HttpServletRequest;

import hu.psprog.dev.jwtdemo.web.auth.JWTPayload;
import hu.psprog.dev.jwtdemo.web.auth.Role;
import hu.psprog.dev.jwtdemo.web.exception.InvalidAuthorizationHeaderException;
import hu.psprog.dev.jwtdemo.web.exception.InvalidJWTTokenException;
import hu.psprog.dev.jwtdemo.web.exception.UnknownJWTIssuerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * JWT encoder/decoder utility.
 * 
 * Application specific JWT secret key shall be provided in security.properties file. 
 */
public class JWTUtility {

    private static final String JWT_USERNAME = "usr";
    private static final String JWT_PASSWORD = "hpw";
    private static final String JWT_ROLE = "rol";
    
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_BEARER = "Bearer ";
    
    private static String secret;
    private static String issuer;

    private JWTUtility() {
        // prevent instantiation
    }
    
    public static void setIssuer(String issuer) {
        JWTUtility.issuer = issuer;
    }
    
    public static void setSecret(String secret) {
        JWTUtility.secret = secret;
    }
    
    /**
     * Decodes given JWT token and returns its payload's content as {@link JWTPayload} object.
     * 
     * @param token given (raw) token
     * @return {@link JWTPayload} object on success with the contents of JWT payload section
     */
    public static JWTPayload decode(String token) {
        
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            
            JWTPayload payload = new JWTPayload();
            payload.setUsername(claims.get(JWT_USERNAME, String.class));
            payload.setHashedPassword(claims.get(JWT_PASSWORD, String.class));
            payload.setExpires(claims.getExpiration());
            payload.setIssuedAt(claims.getIssuedAt());
            payload.setIssuer(claims.getIssuer());
            payload.setRole(Role.valueOf(claims.get(JWT_ROLE, String.class)));
            
            checkIssuer(payload);
            
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
     */
    public static String extractToken(HttpServletRequest request) {

        String authHeader = request.getHeader(AUTH_HEADER);

        if(authHeader == null) {
            return null;
        }
        
        if (!authHeader.startsWith(AUTH_BEARER)) {
            throw new InvalidAuthorizationHeaderException();
        }

        String token = authHeader.substring(AUTH_BEARER.length());

        return token;
    }
    
    /**
     * Checks if given issuer is the same as stored in configuration
     * Throws {@link UnknownJWTIssuerException} on failure.
     * 
     * @param payload payload to check its issuer
     */
    private static void checkIssuer(JWTPayload payload) {
        
        if(!payload.getIssuer().equals(issuer)) {
            
            throw new UnknownJWTIssuerException();
        }
    }
}
