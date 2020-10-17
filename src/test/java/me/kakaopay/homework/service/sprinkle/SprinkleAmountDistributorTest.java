package me.kakaopay.homework.service.sprinkle;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class SprinkleAmountDistributorTest {

    @Test
    public void distribute_1() {

        for (int i = 0; i < 1_000; i++) {
            final BigDecimal amount = BigDecimal.valueOf(RandomUtils.nextLong(1_000, 10_000));
            final int count = RandomUtils.nextInt(1, 100);
            List<BigDecimal> distributed = SprinkleAmountDistributor.distribute(amount, count);
            System.out.println(distributed);
            assertThat(distributed.stream()
                                  .reduce(BigDecimal.ZERO, BigDecimal::add)).isEqualTo(amount);
            assertThat(distributed).hasSize(count);
        }
    }
}