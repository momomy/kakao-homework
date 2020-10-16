package me.kakaopay.homework.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisTestService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void test() {
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set("Test", "Redis!!!");
        redisTemplate.expireAt("Test",
                               LocalDateTime.now()
                                            .plusMinutes(5)
                                            .atZone(ZoneId.systemDefault())
                                            .toInstant());
        log.info("value: {}", vop.get("Test"));
    }
}
