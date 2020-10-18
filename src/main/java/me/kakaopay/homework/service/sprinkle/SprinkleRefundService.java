package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.common.lock.DistributeLock;
import me.kakaopay.homework.common.lock.LockCategory;
import me.kakaopay.homework.entity.BalanceReferenceType;
import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.entity.BalanceSprinkleTransaction;
import me.kakaopay.homework.entity.BalanceTransaction;
import me.kakaopay.homework.entity.SprinkleStatusType;
import me.kakaopay.homework.service.balance.BalanceTransactionService;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleRefundVo;

@RequiredArgsConstructor
@Service
public class SprinkleRefundService {

    private final SprinkleService sprinkleService;

    private final BalanceTransactionService balanceTransactionService;

    @DistributeLock(LockCategory.SPRINKLE_REFUND)
    @Transactional
    public void refund(SprinkleRefundVo vo) {
        sprinkleService.findNotRefund().stream()
                       .filter(this::isExpired)
                       .forEach(sprinkle -> {
                           BigDecimal refundAmount = calculateRefundAmount(sprinkle);
                           if (0 < refundAmount.compareTo(BigDecimal.ZERO)) {
                               BalanceTransaction refundTransaction =
                                       balanceTransactionService.deposit(sprinkle.getUserId(),
                                                                         refundAmount,
                                                                         BalanceReferenceType.SPRINKLE_REFUND);
                               sprinkle.setRefundTransaction(refundTransaction);
                           }
                           sprinkle.setStatus(SprinkleStatusType.REFUNDED);
                       });
    }

    public boolean isExpired(BalanceSprinkle sprinkle) {
        return sprinkle.isExpired()
               || 0 == calculateRefundAmount(sprinkle).compareTo(BigDecimal.ZERO);
    }

    private BigDecimal calculateRefundAmount(BalanceSprinkle sprinkle) {
        final BigDecimal usedAmount = sprinkle.getTransactions().stream()
                                              .map(BalanceSprinkleTransaction::getAmount)
                                              .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sprinkle.getAmount().subtract(usedAmount);
    }

    private boolean isReceivedUser(BalanceSprinkle balanceSprinkle, long userId) {
        return balanceSprinkle.getTransactions().stream()
                              .anyMatch(transaction -> transaction.getUserId() == userId);
    }
}
