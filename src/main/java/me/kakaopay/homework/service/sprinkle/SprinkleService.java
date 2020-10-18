package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.common.lock.DistributeLock;
import me.kakaopay.homework.common.lock.LockCategory;
import me.kakaopay.homework.common.lock.SimpleWaitDistributeLock;
import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.repository.sprinkle.BalanceSprinkleRepository;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleAlreadyReceiveException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleExpiredException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleNotFoundException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleOwnerException;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleCreateVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleReceiveRequestVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleTransactionVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@RequiredArgsConstructor
@Service
public class SprinkleService {

    private static final int TOKEN_LENGTH = 3;

    private static final Duration SPRINKLE_DURATION = Duration.ofMinutes(10);

    private final SprinkleTokenGenerator tokenGenerator;

    private final BalanceSprinkleRepository sprinkleRepository;

    private final SprinkleCacheManager sprinkleCacheManager;

    private final SprinkleTransactionService sprinkleTransactionService;

    @DistributeLock(LockCategory.SPRINKLE_CREATE)
    @Transactional
    public SprinkleVo create(SprinkleCreateVo vo) {
        final String token = tokenGenerator.generate(vo.getUserId(), TOKEN_LENGTH);
        final LocalDateTime sprinkleExpiredAt = LocalDateTime.now().plusSeconds(SPRINKLE_DURATION.getSeconds());
        final SprinkleVo sprinkleVo = SprinkleVo.of(
                sprinkleRepository.save(BalanceSprinkle.create(token,
                                                               vo.getUserId(),
                                                               vo.getRoomId(),
                                                               vo.getCount(),
                                                               vo.getAmount(),
                                                               sprinkleExpiredAt)));

        final List<BigDecimal> distributed = SprinkleAmountDistributor.distribute(vo.getAmount(),
                                                                                  vo.getCount());
        sprinkleCacheManager.save(sprinkleVo, distributed);
        return sprinkleVo;

    }

    @SimpleWaitDistributeLock(LockCategory.SPRINKLE_RECEIVE)
    @Transactional
    public SprinkleTransactionVo receive(SprinkleReceiveRequestVo vo) {
        final SprinkleVo sprinkleVo =
                sprinkleCacheManager.get(vo.getToken())
                                    .orElseThrow(() -> new SprinkleNotFoundException(
                                            "sprinkle not found. vo: " + vo));

        // 같은 방인가?
        if (!sprinkleVo.getRoomId().equals(vo.getRoomId())) {
            throw new SprinkleNotFoundException("sprinkle not found. vo: " + vo);
        }

        // 뿌리가가 만료되었는가?
        if (sprinkleVo.isExpired()) {
            throw new SprinkleExpiredException("sprinkle expired.");
        }

        // 뿌린 유저인가?
        if (sprinkleVo.getUserId() == vo.getUserId()) {
            throw new SprinkleOwnerException("cat not receive owner. vo: " + vo);
        }

        // 남아 있는 분배 금액이 있는가?
        if (!sprinkleCacheManager.isAbleToPop(vo.getToken())) {
            throw new SprinkleExpiredException("sprinkle exhausted. vo: " + vo);
        }

        final BalanceSprinkle balanceSprinkle = sprinkleRepository.get(sprinkleVo.getId());
        // 이미 돈을 받았는가?
        if (isReceivedUser(balanceSprinkle, vo.getUserId())) {
            throw new SprinkleAlreadyReceiveException("already received. vo: " + vo);
        }

        final BigDecimal amount = sprinkleCacheManager.pop(vo.getToken());
        return sprinkleTransactionService.insert(balanceSprinkle, vo.getUserId(), amount);
    }

    private boolean isReceivedUser(BalanceSprinkle balanceSprinkle, long userId) {
        return balanceSprinkle.getTransactions().stream()
                              .anyMatch(transaction -> transaction.getUserId() == userId);
    }

    @Transactional(readOnly = true)
    public SprinkleVo get(long userId, String token) {
        final SprinkleVo sprinkleVo =
                sprinkleCacheManager.get(token)
                                    .filter(vo -> vo.getUserId() == userId)
                                    .orElseThrow(() -> new SprinkleNotFoundException(MessageFormat.format(
                                            "sprinkle not found. userId: {0}, token: {1}", userId, token)));

        return SprinkleVo.of(sprinkleRepository.get(sprinkleVo.getId()));
    }
}
