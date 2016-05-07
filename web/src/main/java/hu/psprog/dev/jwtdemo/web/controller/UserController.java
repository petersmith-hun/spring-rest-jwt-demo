package hu.psprog.dev.jwtdemo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.psprog.dev.jwtdemo.service.UserService;
import hu.psprog.dev.jwtdemo.web.model.JWTAuthenticationAnswerModel;
import hu.psprog.dev.jwtdemo.web.model.JWTAuthenticationRequestModel;
import hu.psprog.dev.jwtdemo.web.util.JWTUtility;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes = "application/json")
    public JWTAuthenticationAnswerModel requestToken(@RequestBody JWTAuthenticationRequestModel requestBody) {
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(requestBody.getUsername(), requestBody.getPassword());
        authenticationManager.authenticate(authentication);

        UserDetails user = userService.loadUserByUsername(requestBody.getUsername());
        
        return JWTUtility.generateToken(user);
    }
    
    @RequestMapping("/user/register")
    public String registerUser() {
        
        return "registration";
    }
}
