package me.kakaopay.homework.exception;

/**
 * Token을 생성하지 못한경우
 */
public class TokenGenerationException extends RuntimeException {

    private static final long serialVersionUID = 7008257753684296983L;

    public TokenGenerationException(String message) {
        super(message);
    }
}
