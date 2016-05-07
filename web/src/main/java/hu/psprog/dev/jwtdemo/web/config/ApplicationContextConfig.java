package hu.psprog.dev.jwtdemo.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "hu.psprog.dev.jwtdemo.web.config",
        "hu.psprog.dev.jwtdemo.web.controller",
        "hu.psprog.dev.jwtdemo.service"})
public class ApplicationContextConfig {

}
