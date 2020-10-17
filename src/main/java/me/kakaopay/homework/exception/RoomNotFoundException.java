package me.kakaopay.homework.exception;

/**
 * Room 을 찾지 못한 경우
 */
public class RoomNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3760774895287223625L;

    public RoomNotFoundException(String message) {
        super(message);
    }
}
