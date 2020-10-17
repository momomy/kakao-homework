package me.kakaopay.homework.common.lock;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import me.kakaopay.homework.common.lock.exceptioon.AlreadyLockedException;
import me.kakaopay.homework.common.lock.exceptioon.LockKeyNotBlankException;
import me.kakaopay.homework.common.lock.exceptioon.NoLockableArgumentException;
import me.kakaopay.homework.common.lock.exceptioon.TooManyLockableArgumentException;

@Slf4j
public abstract class AbstractDistributeLockAspect {
    private static final String KEY_PREFIX = "DL";
    private static final String KEY_DELIMITER = ":";

    @Resource
    protected RedisTemplate<String, Boolean> redisTemplate;

    protected Object around(ProceedingJoinPoint joinPoint, LockCategory lockCategory) throws Throwable {
        final String key = makeKey(lockCategory, getLockKey(joinPoint.getArgs()));

        boolean locked = false;
        try {
            locked = lock(lockCategory, key);
            if (!locked) {
                throw new AlreadyLockedException("Already locked. key=" + key);
            }

            return joinPoint.proceed();
        } finally {
            if (locked) {
                unlock(key);
            }
        }
    }

    protected abstract boolean lock(LockCategory lockCategory, String key);

    private void unlock(String key) {
        log.debug("unlock. key={}", key);
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("failed to unlock.", e);
        }
    }

    private static String getLockKey(Object[] args) {
        final List<Lockable> lockableList = Arrays.stream(args)
                                                  .filter(arg -> arg instanceof Lockable)
                                                  .map(arg -> (Lockable) arg)
                                                  .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(lockableList)) {
            throw new NoLockableArgumentException("Lockable type parameter does not exist.");
        }

        if (lockableList.size() > 1) {
            throw new TooManyLockableArgumentException("Too many Lockable type parameters.");
        }

        final String lockKey = lockableList.get(0).getLockKey();
        if (StringUtils.isBlank(lockKey)) {
            throw new LockKeyNotBlankException("lockKey value cannot be blank.");
        }

        return lockKey;
    }

    private static String makeKey(LockCategory category, String key) {
        return KEY_PREFIX + KEY_DELIMITER + category.name() + KEY_DELIMITER + key;
    }
}
