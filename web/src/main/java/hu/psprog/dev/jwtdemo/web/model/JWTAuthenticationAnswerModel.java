package hu.psprog.dev.jwtdemo.web.model;

import java.io.Serializable;

public class JWTAuthenticationAnswerModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String token;
    
    public JWTAuthenticationAnswerModel() {
        // Serializable
    }

    public JWTAuthenticationAnswerModel(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JWTAuthenticationAnswerModel [token=" + token + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((token == null) ? 0 : token.hashCode());
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
        JWTAuthenticationAnswerModel other = (JWTAuthenticationAnswerModel) obj;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        return true;
    }
}
