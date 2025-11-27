package com.example.springserver;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello from Spring Server!";
    }

    @GetMapping("/api/status")
    public String status() {
        return "Server is running OK";
    }
}

