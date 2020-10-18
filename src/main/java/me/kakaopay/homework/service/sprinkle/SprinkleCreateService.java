package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.common.lock.DistributeLock;
import me.kakaopay.homework.common.lock.LockCategory;
import me.kakaopay.homework.entity.BalanceReferenceType;
import me.kakaopay.homework.entity.BalanceTransaction;
import me.kakaopay.homework.repository.sprinkle.BalanceSprinkleRepository;
import me.kakaopay.homework.service.balance.BalanceTransactionService;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleCreateVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@RequiredArgsConstructor
@Service
public class SprinkleCreateService {

    private static final int TOKEN_LENGTH = 3;

    private static final Duration SPRINKLE_DURATION = Duration.ofMinutes(10);

    private final SprinkleTokenGenerator tokenGenerator;

    private final SprinkleCacheManager sprinkleCacheManager;

    private final BalanceTransactionService balanceTransactionService;

    private final SprinkleService sprinkleService;

    @DistributeLock(LockCategory.SPRINKLE_CREATE)
    @Transactional
    public SprinkleVo create(SprinkleCreateVo vo) {
        final String token = tokenGenerator.generate(vo.getUserId(), TOKEN_LENGTH);
        final LocalDateTime sprinkleExpiredAt = LocalDateTime.now().plusSeconds(SPRINKLE_DURATION.getSeconds());

        final BalanceTransaction sprinkleTransaction = balanceTransactionService.withdraw(
                vo.getUserId(), vo.getAmount(), BalanceReferenceType.SPRINKLE);

        final SprinkleVo sprinkleVo = SprinkleVo.of(sprinkleService.insert(token,
                                                                           vo.getUserId(),
                                                                           vo.getRoomId(),
                                                                           vo.getCount(),
                                                                           vo.getAmount(),
                                                                           sprinkleExpiredAt,
                                                                           sprinkleTransaction));

        final List<BigDecimal> distributed = SprinkleAmountDistributor.distribute(vo.getAmount(),
                                                                                  vo.getCount());
        sprinkleCacheManager.save(sprinkleVo, distributed);
        return sprinkleVo;

    }
}
