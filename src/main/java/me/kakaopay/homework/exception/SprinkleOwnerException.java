package me.kakaopay.homework.exception;

/**
 * 본인이 뿌리기를 받으려 햐는 경우
 */
public class SprinkleOwnerException extends RuntimeException {

    private static final long serialVersionUID = -5132810405197079464L;

    public SprinkleOwnerException(String message) {
        super(message);
    }
}
