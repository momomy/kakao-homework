package me.kakaopay.homework.exception;

/**
 * 이미 뿌리기를 받은 경우
 */
public class SprinkleAlreadyReceiveException extends RuntimeException {

    private static final long serialVersionUID = -4291959185186540380L;

    public SprinkleAlreadyReceiveException(String message) {
        super(message);
    }
}
