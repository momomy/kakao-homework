package me.kakaopay.homework.common.lock.exceptioon;

public class TooManyLockableArgumentException extends RuntimeException {

    private static final long serialVersionUID = -131436889341670560L;

    public TooManyLockableArgumentException(String message) {
        super(message);
    }
}
