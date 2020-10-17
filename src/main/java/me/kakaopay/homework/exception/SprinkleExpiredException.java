package me.kakaopay.homework.exception;

/**
 * 뿌리기가 종료 된 경우
 */
public class SprinkleExpiredException extends RuntimeException {

    private static final long serialVersionUID = 6374248964354146790L;

    public SprinkleExpiredException(String message) {
        super(message);
    }
}
