package me.kakaopay.homework.service.sprinkle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 뿌리기가 종료 된 경우
 */
@ResponseStatus(value = HttpStatus.GONE, reason = "sprinkle expired.")
public class SprinkleExpiredException extends RuntimeException {

    private static final long serialVersionUID = 6374248964354146790L;

    public SprinkleExpiredException(String message) {
        super(message);
    }
}
