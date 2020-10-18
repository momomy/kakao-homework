package me.kakaopay.homework.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello homework!";
    }
}
