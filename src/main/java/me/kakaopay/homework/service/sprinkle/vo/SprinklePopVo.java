package me.kakaopay.homework.service.sprinkle.vo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SprinklePopVo {

    private final SprinkleVo sprinkleVo;

    private final BigDecimal amount;

    public static SprinklePopVo of(SprinkleVo sprinkleVo, BigDecimal amount) {
        return new SprinklePopVo(sprinkleVo, amount);
    }
}
