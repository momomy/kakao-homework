package me.kakaopay.homework.exception;

public class SprinkleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1899493588375433284L;

    public SprinkleNotFoundException(String message) {
        super(message);
    }
}
