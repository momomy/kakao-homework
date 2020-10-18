package me.kakaopay.homework.service.sprinkle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 뿌리기를 찾지 못한 경우
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "sprinkle not found.")
public class SprinkleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1899493588375433284L;

    public SprinkleNotFoundException(String message) {
        super(message);
    }
}
