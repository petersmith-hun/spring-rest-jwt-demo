package hu.psprog.dev.jwtdemo.web.model;

import java.io.Serializable;

public class DemoModel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String message;
    
    public DemoModel() {
        // Serializable
    }
    
    public DemoModel(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
