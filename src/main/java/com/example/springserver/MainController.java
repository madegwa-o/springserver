package com.example.springserver;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class MainController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final Environment environment;



    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Server!";
    }

    @GetMapping("/status")
    public String status() {
        return "Server is running OK";
    }

    @GetMapping("/secret-check")
    public String secretCheck() {

        String jwtSeretEnv =  environment.getProperty("jwt.secret");

        return "JWT_SECRET is " + (jwtSecret != null ? jwtSecret : "NOT FOUND");
    }

}

