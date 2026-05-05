package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/fail")
    public String fail() {
        throw new RuntimeException("Test error");
    }
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello Aditya Mane welcometo Kubernetes! 🚀";
    }
}