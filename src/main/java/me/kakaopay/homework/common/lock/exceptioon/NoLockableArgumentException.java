package me.kakaopay.homework.common.lock.exceptioon;

public class NoLockableArgumentException extends RuntimeException {

    private static final long serialVersionUID = 7359426806208535285L;

    public NoLockableArgumentException(String message) {
        super(message);
    }
}
