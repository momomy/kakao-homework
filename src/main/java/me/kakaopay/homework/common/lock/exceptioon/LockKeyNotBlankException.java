package me.kakaopay.homework.common.lock.exceptioon;

public class LockKeyNotBlankException extends RuntimeException {

    private static final long serialVersionUID = 2207275354064768287L;

    public LockKeyNotBlankException(String message) {
        super(message);
    }
}
