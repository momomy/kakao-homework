package me.kakaopay.homework.common.lock.exceptioon;

public class AlreadyLockedException extends RuntimeException {

    private static final long serialVersionUID = 6950485604760904268L;

    public AlreadyLockedException(String message) {
        super(message);
    }
}
