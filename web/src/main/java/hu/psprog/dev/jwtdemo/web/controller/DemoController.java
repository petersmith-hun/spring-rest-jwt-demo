package hu.psprog.dev.jwtdemo.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.psprog.dev.jwtdemo.web.model.DemoModel;

@RestController
public class DemoController {

    @RequestMapping("/echo")
    public DemoModel echo() {
        
        return new DemoModel("This is a demo message");
    }
}
