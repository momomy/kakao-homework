package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class SprinkleAmountDistributor {
    /**
     * 금액을 분배한다.
     * .평균 값을 구한다.
     * .평균 값을 기준으로 Random 값을 생성하여 하나의 금액에는 더하고, 다음금액에는 뺀다.
     */
    public static List<BigDecimal> distribute(BigDecimal amount, int count) {
        final List<BigDecimal> distributedAmounts = Lists.newArrayList();
        final BigDecimal average = amount.divide(BigDecimal.valueOf(count), 0, RoundingMode.DOWN);
        BigDecimal remainAmount = amount;
        BigDecimal offset = BigDecimal.ZERO;
        for (int i = 0; i < count; i++) {
            BigDecimal distributed;
            if (0 == i % 2) {
                offset = BigDecimal.valueOf(RandomUtils.nextLong(0, average.longValue()));
                distributed = average.subtract(offset);
            } else {
                distributed = average.add(offset);
            }

            remainAmount = remainAmount.subtract(distributed);
            if (i == count - 1) {
                distributed = distributed.add(remainAmount);
            }
            distributedAmounts.add(distributed);
        }
        Collections.shuffle(distributedAmounts);
        return distributedAmounts;
    }
}
