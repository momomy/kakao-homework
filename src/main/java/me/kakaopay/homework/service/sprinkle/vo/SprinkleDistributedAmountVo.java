package me.kakaopay.homework.service.sprinkle.vo;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SprinkleDistributedAmountVo {
    private final BigDecimal amount;
    private final List<BigDecimal> distributed;

    /**
     * 종료되었는가?
     */
    public boolean isFinish() {
        return 0L == getRemainCount();
    }

    /**
     * 남은개수
     */
    public int getRemainCount() {
        return distributed.size();
    }

    public static SprinkleDistributedAmountVo of(BigDecimal amount) {
        return new SprinkleDistributedAmountVo(amount, Lists.newArrayList());
    }

    public static SprinkleDistributedAmountVo of(BigDecimal amount, List<BigDecimal> distributed) {
        return new SprinkleDistributedAmountVo(amount, distributed);
    }
}
