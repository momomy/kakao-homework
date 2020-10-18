package me.kakaopay.homework.service.sprinkle;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 뿌리기에 사용 할 Token을 생성한다.
 */
@RequiredArgsConstructor
@Service
class SprinkleTokenGenerator {

    private final SprinkleCacheManager sprinkleCacheManager;

    private static final int RETRY_COUNT = 10;

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String generate(long userId, int length) {
        for (int i = 0; i < RETRY_COUNT; i++) {
            final String token = RandomStringUtils.randomAlphanumeric(length);
            if (!(sprinkleCacheManager.contain(token))) {
                return token;
            }
        }
        throw new IllegalStateException("can not generate sprinkle token. user: " + userId);
    }
}
