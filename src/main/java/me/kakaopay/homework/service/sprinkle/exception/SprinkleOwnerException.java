package me.kakaopay.homework.service.sprinkle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 본인이 뿌리기를 받으려 햐는 경우
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "already received.")
public class SprinkleOwnerException extends RuntimeException {

    private static final long serialVersionUID = -5132810405197079464L;

    public SprinkleOwnerException(String message) {
        super(message);
    }
}
