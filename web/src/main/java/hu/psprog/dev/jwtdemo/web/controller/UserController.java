package hu.psprog.dev.jwtdemo.web.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.psprog.dev.jwtdemo.web.model.JWTAuthenticationRequestModel;

@RestController
public class UserController {

    @RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes = "application/json")
    public JWTAuthenticationRequestModel requestToken(@RequestBody JWTAuthenticationRequestModel requestBody) {
        
        return requestBody;
    }
    
    @RequestMapping("/user/register")
    public String registerUser() {
        
        return "registration";
    }
}
