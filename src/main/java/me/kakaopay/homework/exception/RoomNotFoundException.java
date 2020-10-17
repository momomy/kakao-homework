package me.kakaopay.homework.exception;

public class RoomNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3760774895287223625L;

    public RoomNotFoundException(String message) {
        super(message);
    }
}
