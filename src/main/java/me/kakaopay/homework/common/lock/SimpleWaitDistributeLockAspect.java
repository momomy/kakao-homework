package me.kakaopay.homework.common.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class SimpleWaitDistributeLockAspect extends AbstractDistributeLockAspect {

    private static final long SLEEP_MILLIS = 50;

    @Around("@annotation(distributeLock)")
    public Object around(ProceedingJoinPoint joinPoint, SimpleWaitDistributeLock distributeLock)
            throws Throwable {
        return around(joinPoint, distributeLock.value());
    }

    /**
     * Wait Timeout 적용
     * 참고: https://redislabs.com/ebook/part-2-core-concepts/chapter-6-application-components-in-redis/6-2-distributed-locking/6-2-5-locks-with-timeouts/
     */
    @Override
    protected boolean lock(LockCategory lockCategory, String key) {
        log.debug("lock. lockCategory={}, key={}", lockCategory, key);
        final long end = System.currentTimeMillis() + lockCategory.getWait().toMillis();
        while (System.currentTimeMillis() < end) {
            final Boolean locked = redisTemplate.opsForValue()
                                                .setIfAbsent(key, Boolean.TRUE, lockCategory.getExpire());
            if (Boolean.TRUE.equals(locked)) {
                return true;
            }

            try {
                Thread.sleep(SLEEP_MILLIS);
            } catch (InterruptedException e) {
                log.warn("fail to sleep.", e);
            }
        }
        return false;
    }
}
