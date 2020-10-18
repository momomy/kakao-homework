package me.kakaopay.homework.common.lock;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LockCategory {
    SPRINKLE_CREATE(Duration.ofMinutes(5), Duration.ZERO),
    SPRINKLE_RECEIVE(Duration.ofMinutes(5), Duration.ofMinutes(1));

    private final Duration expire;
    private final Duration wait;

}
