package com.example.authServer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class General {
    @GetMapping("/health")
    public ResponseEntity<Map<String,Object>> healthCheck() {
        Map<String,Object> response = new HashMap<>();
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public void test() {
        System.out.println("Test");
    }
}
