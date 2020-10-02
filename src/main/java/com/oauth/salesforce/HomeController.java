package com.oauth.salesforce;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @RequestMapping(value = "/callback", produces = MediaType.TEXT_HTML_VALUE)
    @ModelAttribute
    public String getHome(@RequestParam(name = "s", defaultValue = "default") String value, Model model) {
        model.addAttribute("message", value);
        return "callback.html";
    }
}
