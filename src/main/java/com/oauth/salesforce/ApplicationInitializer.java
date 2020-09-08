package com.oauth.salesforce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class ApplicationInitializer {
    @RequestMapping("/")
    public String homePage() {
        return "Welcome!";
    }

    @RequestMapping(value = "/callback", produces = MediaType.TEXT_HTML_VALUE)
    public String callbackPage() {
        return "callback";
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationInitializer.class, args);
    }
}
