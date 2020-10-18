package me.kakaopay.homework.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtils {
    public static Instant toInstant(LocalDateTime at) {
        return at.atZone(ZoneId.systemDefault()).toInstant();
    }
}
