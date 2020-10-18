package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.common.utils.LocalDateTimeUtils;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@RequiredArgsConstructor
@Service
class SprinkleCacheManager {

    private static final String SPRINKLE_KEY_PREFIX = "sprinkle-cache";
    private static final String SPRINKLE_DISTRIBUTE_KEY_PREFIX = "sprinkle-cache-distributed";

    private static final int CACHE_DURATION_DAYS = 7;

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, BigDecimal> decimalRedisTemplate;

    /**
     * 7일 동안 유효한 뿌리기 Cache를 생성한다.
     * 분배금액도 Cache에 저장한다.
     * Cache Key는 Token 이다.
     */
    public void save(SprinkleVo sprinkleVo, List<BigDecimal> distributed) {
        final String sprinkleKey = makeKey(SPRINKLE_KEY_PREFIX, sprinkleVo.getToken());
        final String distributedKey = makeKey(SPRINKLE_DISTRIBUTE_KEY_PREFIX, sprinkleVo.getToken());

        if (Optional.ofNullable(redisTemplate.hasKey(sprinkleKey)).orElse(false)) {
            throw new IllegalStateException("sprinkle key is exists. key: " + sprinkleKey);
        }

        final Instant expiredAt = getCacheExpiredAt(sprinkleVo);

        // Sprinkle VO 저장
        redisTemplate.opsForValue().set(sprinkleKey, sprinkleVo);
        redisTemplate.expireAt(sprinkleKey, expiredAt);

        // Sprinkle 분배금액 저장
        ListOperations<String, BigDecimal> listOperations = decimalRedisTemplate.opsForList();
        listOperations.leftPushAll(distributedKey, distributed);
        decimalRedisTemplate.expireAt(distributedKey, expiredAt);
    }

    private Instant getCacheExpiredAt(SprinkleVo sprinkleVo) {
        return LocalDateTimeUtils.toInstant(
                sprinkleVo.getCreatedAt().plusDays(CACHE_DURATION_DAYS).toLocalDate().atStartOfDay());
    }

    /**
     * Cache에 저장되어 있는가?
     */
    public boolean contain(String token) {
        final String sprinkleKey = makeKey(SPRINKLE_KEY_PREFIX, token);
        return Optional.ofNullable(redisTemplate.hasKey(sprinkleKey)).orElse(false);
    }

    /**
     * Sprinkle 정보를 가져온다.
     */
    public Optional<SprinkleVo> get(String token) {
        final String sprinkleKey = makeKey(SPRINKLE_KEY_PREFIX, token);
        return Optional.ofNullable((SprinkleVo) redisTemplate.opsForValue().get(sprinkleKey));

    }

    /**
     * 분배 된 금액이 남아 있는가?
     */
    public boolean isAbleToPop(String token) {
        final String distributedKey = makeKey(SPRINKLE_DISTRIBUTE_KEY_PREFIX, token);
        final ListOperations<String, BigDecimal> listOperations = decimalRedisTemplate.opsForList();
        return 0 < Optional.ofNullable(listOperations.size(distributedKey)).orElse(0L);
    }

    /**
     * 분배 된 금액을 가져온다.
     */
    public BigDecimal pop(String token) {
        final String distributedKey = makeKey(SPRINKLE_DISTRIBUTE_KEY_PREFIX, token);
        final ListOperations<String, BigDecimal> listOperations = decimalRedisTemplate.opsForList();
        return Optional.ofNullable(listOperations.rightPop(distributedKey))
                       .orElseThrow(
                               () -> new IllegalStateException("can not pop amount. key: " + distributedKey));
    }

    private String makeKey(String prefix, String token) {
        return Joiner.on('-').join(prefix, token);
    }

}
