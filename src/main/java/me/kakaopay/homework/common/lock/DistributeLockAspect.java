package me.kakaopay.homework.common.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class DistributeLockAspect extends AbstractDistributeLockAspect {

    @Around("@annotation(distributeLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributeLock distributeLock) throws Throwable {
        return around(joinPoint, distributeLock.value());
    }

    @Override
    protected boolean lock(LockCategory lockCategory, String key) {
        final Boolean locked = redisTemplate.opsForValue()
                                            .setIfAbsent(key, Boolean.TRUE, lockCategory.getExpire());
        return Boolean.TRUE.equals(locked);
    }
}
