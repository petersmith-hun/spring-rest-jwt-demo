package hu.psprog.dev.jwtdemo.web.auth;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * JWT content wrapper.
 */
public class JWTPayload implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Standard Issued at field in payload ("iat").
     */
    @NotNull
    private Date issuedAt;
    
    /**
     * Standard Expiration time field in payload ("exp");
     */
    @NotNull
    private Date expires;
    
    /**
     * Username of user who claimed the token.
     */
    @NotNull
    private String username;
    
    /**
     * User role.
     */
    @NotNull
    private Role role;
    
    public JWTPayload() {
        // Serializable
    }

    public JWTPayload(Date issuedAt, Date expires, String username, Role role) {
        super();
        this.issuedAt = issuedAt;
        this.expires = expires;
        this.username = username;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "JWTPayload [issuedAt=" + issuedAt + ", expires=" + expires + ", username=" + username + ", role=" + role
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expires == null) ? 0 : expires.hashCode());
        result = prime * result + ((issuedAt == null) ? 0 : issuedAt.hashCode());
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
        if (issuedAt == null) {
            if (other.issuedAt != null)
                return false;
        } else if (!issuedAt.equals(other.issuedAt))
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
