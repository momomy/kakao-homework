package me.kakaopay.homework.exception;

/**
 * 뿌리기를 찾지 못한 경우
 */
public class SprinkleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1899493588375433284L;

    public SprinkleNotFoundException(String message) {
        super(message);
    }
}
