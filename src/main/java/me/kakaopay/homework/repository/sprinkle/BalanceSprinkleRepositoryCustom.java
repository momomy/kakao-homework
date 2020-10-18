package me.kakaopay.homework.repository.sprinkle;

import java.util.List;

import me.kakaopay.homework.entity.BalanceSprinkle;

public interface BalanceSprinkleRepositoryCustom {
    BalanceSprinkle get(long id);

    List<BalanceSprinkle> findNotRefund();
}
