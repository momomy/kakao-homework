package me.kakaopay.homework.service.sprinkle;

import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.entity.User;
import me.kakaopay.homework.exception.TokenGenerationException;
import me.kakaopay.homework.repository.sprinkle.BalanceSprinkleRepository;

/**
 * 뿌리기에 사용 할 Token을 생성한다.
 */
@RequiredArgsConstructor
@Service
class SprinkleTokenGenerator {

    private final BalanceSprinkleRepository balanceSprinkleRepository;

    private static final int RETRY_COUNT = 10;

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String generate(User user, int length) {
        final Set<String> tokenSet = balanceSprinkleRepository.findTokens(user);
        for (int i = 0; i < RETRY_COUNT; i++) {
            final String token = RandomStringUtils.randomAlphanumeric(length);
            if (!tokenSet.contains(token)) {
                return token;
            }
        }
        throw new TokenGenerationException("can not generate sprinkle token. user: " + user.getXid());
    }
}
