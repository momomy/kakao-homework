package me.kakaopay.homework.common.lock;

@FunctionalInterface
public interface Lockable {
    String getLockKey();
}
