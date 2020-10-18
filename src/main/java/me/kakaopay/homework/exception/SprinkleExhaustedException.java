package me.kakaopay.homework.exception;

/**
 * 뿌리기가 소진 된 경우
 */
public class SprinkleExhaustedException extends RuntimeException {

    private static final long serialVersionUID = -5397049632811894209L;

    public SprinkleExhaustedException(String message) {
        super(message);
    }
}
