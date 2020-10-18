package me.kakaopay.homework.common.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * SimpleWaitDistributeLock
 * 50ms Sleep time 을 주어 wait time 동안 lock을 획득한다.
 *
 * 주의점:
 * - 50ms 마다 Redis를 호출 하기 때문에 Redis 에 부하가 많이 갈 수 있다.
 *   자주 wait 가 발생 할 수 있는 로직이라면 사용하지 않는 것이 좋다.
 * - 순서 보장이 되지 않는다.
 */
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleWaitDistributeLock {
    LockCategory value();
}
