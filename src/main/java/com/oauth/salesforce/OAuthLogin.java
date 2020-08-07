package com.oauth.salesforce;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthLogin {
    private static final String template = "Hello, %s!";

    @GetMapping("/login")
    public String login(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Example";
    }
}
