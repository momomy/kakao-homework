package me.kakaopay.homework.controller.sprinkle;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SprinkleTransactionDto {

    /**
     * 받은 유저
     */
    private final long userXid;

    /**
     * 받은 금액
     */
    private final BigDecimal amount;

    public static SprinkleTransactionDto of(long userXid, BigDecimal amount) {
        return new SprinkleTransactionDto(userXid, amount);
    }
}
