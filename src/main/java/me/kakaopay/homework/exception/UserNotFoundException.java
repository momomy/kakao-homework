package me.kakaopay.homework.exception;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -473810780280564852L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
