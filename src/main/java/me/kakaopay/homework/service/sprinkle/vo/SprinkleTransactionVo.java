package me.kakaopay.homework.service.sprinkle.vo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kakaopay.homework.controller.sprinkle.dto.SprinkleTransactionDto;
import me.kakaopay.homework.entity.BalanceSprinkleTransaction;

@AllArgsConstructor
@Getter
public class SprinkleTransactionVo {
    private final long userId;

    private final BigDecimal amount;

    public static SprinkleTransactionVo of(long userId, BigDecimal amount) {
        return new SprinkleTransactionVo(userId, amount);
    }

    public static SprinkleTransactionVo of(BalanceSprinkleTransaction transaction) {
        return of(transaction.getUserId(), transaction.getAmount());
    }

    public SprinkleTransactionDto toDto() {
        return SprinkleTransactionDto.of(userId, amount);
    }
}
