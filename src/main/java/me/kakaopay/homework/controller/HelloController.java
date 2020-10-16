package me.kakaopay.homework.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.service.RedisTestService;

@RequiredArgsConstructor
@RestController
public class HelloController {

    private final RedisTestService redisTestService;

    @GetMapping("/hello")
    public String hello() {
        redisTestService.test();
        return "Hello homework!";
    }
}
