package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.common.lock.LockCategory;
import me.kakaopay.homework.common.lock.SimpleWaitDistributeLock;
import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleAlreadyReceiveException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleExpiredException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleNotFoundException;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleOwnerException;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleReceiveRequestVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleTransactionVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@RequiredArgsConstructor
@Service
public class SprinkleReceiveService {

    private final SprinkleService sprinkleService;

    private final SprinkleCacheManager sprinkleCacheManager;

    private final SprinkleTransactionService sprinkleTransactionService;

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

        final BalanceSprinkle balanceSprinkle = sprinkleService.get(sprinkleVo.getId());
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
}
