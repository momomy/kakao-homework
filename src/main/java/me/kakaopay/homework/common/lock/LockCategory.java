package me.kakaopay.homework.common.lock;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LockCategory {
    SPRINKLE_CREATE(Duration.ofMinutes(5)),
    SPRINKLE_RECEIVE(Duration.ofMinutes(5));

    private final Duration expire;

}
