package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
    @ResponseBody
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
