package me.kakaopay.homework.service.sprinkle.vo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kakaopay.homework.controller.sprinkle.SprinkleTransactionDto;
import me.kakaopay.homework.entity.SprinkleTransaction;

@AllArgsConstructor
@Getter
public class SprinkleTransactionVo {
    private final long userXid;

    private final BigDecimal amount;

    public static SprinkleTransactionVo of(long userXid, BigDecimal amount) {
        return new SprinkleTransactionVo(userXid, amount);
    }

    public static SprinkleTransactionVo of(SprinkleTransaction transaction) {
        return of(transaction.getUser().getXid(), transaction.getAmount());
    }

    public SprinkleTransactionDto toDto() {
        return SprinkleTransactionDto.of(userXid, amount);
    }
}
