package me.kakaopay.homework.exception;

/**
 * User를 찾지 못한 경우
 */
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -473810780280564852L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
