package com.chatterbox.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/")
public class HelloController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public String hello(){
        return "hello";
    }
}
