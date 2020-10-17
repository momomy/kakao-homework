package me.kakaopay.homework.repository.sprinkle;

import java.util.Optional;
import java.util.Set;

import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.entity.User;

public interface BalanceSprinkleRepositoryCustom {
    Optional<BalanceSprinkle> find(User user, String token);

    Set<String> findTokens(User user);
}
