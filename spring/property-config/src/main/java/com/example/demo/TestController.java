package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConfigurationProperties
public class TestController {

    private String name;

    @GetMapping("/getName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
