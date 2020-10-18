package me.kakaopay.homework.service.balance;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.entity.BalanceReferenceType;
import me.kakaopay.homework.entity.BalanceTransaction;
import me.kakaopay.homework.repository.blanace.BalanceTransactionRepository;

@RequiredArgsConstructor
@Service
public class BalanceTransactionService {
    private final BalanceTransactionRepository balanceTransactionRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public BalanceTransaction deposit(
            long userId, BigDecimal amount, long referenceUserId, BalanceReferenceType referenceType) {
        return balanceTransactionRepository.save(
                BalanceTransaction.deposit(userId, amount, referenceUserId, referenceType));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public BalanceTransaction withdraw(
            long userId, BigDecimal amount, long referenceUserId, BalanceReferenceType referenceType) {
        return balanceTransactionRepository.save(
                BalanceTransaction.withdraw(userId, amount, referenceUserId, referenceType));
    }

}
