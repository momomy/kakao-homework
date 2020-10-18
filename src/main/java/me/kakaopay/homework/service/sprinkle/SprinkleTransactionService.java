package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.entity.BalanceReferenceType;
import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.entity.BalanceSprinkleTransaction;
import me.kakaopay.homework.entity.BalanceTransaction;
import me.kakaopay.homework.repository.sprinkle.BalanceSprinkleTransactionRepository;
import me.kakaopay.homework.service.balance.BalanceTransactionService;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleTransactionVo;

@RequiredArgsConstructor
@Service
public class SprinkleTransactionService {

    private final BalanceTransactionService balanceTransactionService;
    private final BalanceSprinkleTransactionRepository sprinkleTransactionRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public SprinkleTransactionVo insert(BalanceSprinkle balanceSprinkle, long userId, BigDecimal amount) {
        final BalanceTransaction balanceTransaction = balanceTransactionService.deposit(
                userId, amount, balanceSprinkle.getUserId(), BalanceReferenceType.SPRINKLE_RECEIVE);

        final BalanceSprinkleTransaction sprinkleTransaction = sprinkleTransactionRepository.save(
                BalanceSprinkleTransaction.create(balanceSprinkle, userId, amount, balanceTransaction));

        return SprinkleTransactionVo.of(sprinkleTransaction);
    }
}
