package hu.psprog.dev.jwtdemo.web.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * JWT content wrapper.
 */
public class JWTPayload implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * Standard Issuer field in payload ("iss").
     * 
     * Value shall be the same as specified by jwt.issuer property in security.properties file.
     */
    private String issuer;
    
    /**
     * Standard Issued at field in payload ("iat").
     */
    private Date issuedAt;
    
    /**
     * Standard Expiration time field in payload ("exp");
     */
    private Date expires;
    
    /**
     * Username of user who claimed the token.
     */
    private String username;
    
    /**
     * Role of user.
     */
    private Role role;
    
    /**
     * Hashed password of user who claimed the token.
     */
    private String hashedPassword;
    
    public JWTPayload() {
        // Serializable
    }
    
    public JWTPayload(String issuer, Date issuedAt, Date expires, String username, String hashedPassword, Role role) {
        super();
        this.issuer = issuer;
        this.issuedAt = issuedAt;
        this.expires = expires;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "JWTPayload [issuer=" + issuer + ", issuedAt=" + issuedAt + ", expires=" + expires + ", username="
                + username + ", role=" + role + ", hashedPassword=" + hashedPassword + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expires == null) ? 0 : expires.hashCode());
        result = prime * result + ((hashedPassword == null) ? 0 : hashedPassword.hashCode());
        result = prime * result + ((issuedAt == null) ? 0 : issuedAt.hashCode());
        result = prime * result + ((issuer == null) ? 0 : issuer.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JWTPayload other = (JWTPayload) obj;
        if (expires == null) {
            if (other.expires != null)
                return false;
        } else if (!expires.equals(other.expires))
            return false;
        if (hashedPassword == null) {
            if (other.hashedPassword != null)
                return false;
        } else if (!hashedPassword.equals(other.hashedPassword))
            return false;
        if (issuedAt == null) {
            if (other.issuedAt != null)
                return false;
        } else if (!issuedAt.equals(other.issuedAt))
            return false;
        if (issuer == null) {
            if (other.issuer != null)
                return false;
        } else if (!issuer.equals(other.issuer))
            return false;
        if (role != other.role)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
}
