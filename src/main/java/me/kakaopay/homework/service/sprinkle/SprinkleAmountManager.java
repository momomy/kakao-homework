package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.exception.SprinkleExpiredException;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleDistributedAmountVo;

@RequiredArgsConstructor
@Service
class SprinkleAmountManager {

    private final RedisTemplate<String, BigDecimal> decimalRedisTemplate;

    /**
     * 금액을 분배하고 Redis 에 저장한다.
     */
    public void distribute(
            long userXid, String token, BigDecimal amount, int count, LocalDateTime expiredAt) {
        final String key = makeKey(userXid, token);
        if (Optional.ofNullable(decimalRedisTemplate.hasKey(key)).orElse(false)) {
            throw new IllegalStateException("sprinkle amount key is exists. key: " + key);
        }

        final List<BigDecimal> distributed = SprinkleAmountDistributor.distribute(amount, count);
        ListOperations<String, BigDecimal> listOperations = decimalRedisTemplate.opsForList();
        listOperations.leftPushAll(key, distributed);
        decimalRedisTemplate.expireAt(key, expiredAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 분배 된 금액을 가져온다.
     */
    public SprinkleDistributedAmountVo pop(long userXid, String token) {
        final String key = makeKey(userXid, token);
        final ListOperations<String, BigDecimal> listOperations = decimalRedisTemplate.opsForList();
        final BigDecimal amount = Optional.ofNullable(listOperations.rightPop(key))
                                          .orElseThrow(() -> new SprinkleExpiredException(
                                                  "sprinkle expired. token: " + token));
        final long size = Optional.ofNullable(listOperations.size(key)).orElse(0L);
        if (0L == size) {
            decimalRedisTemplate.delete(key);
            return SprinkleDistributedAmountVo.of(amount);
        }

        return SprinkleDistributedAmountVo.of(amount,
                                              Optional.ofNullable(listOperations.range(key, 0, -1))
                                                      .orElse(Lists.newArrayList()));
    }

    private String makeKey(long userXid, String token) {
        return Joiner.on('-').join(userXid, token);
    }

}
