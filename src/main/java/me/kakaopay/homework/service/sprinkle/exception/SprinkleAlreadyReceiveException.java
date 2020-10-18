package me.kakaopay.homework.service.sprinkle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 이미 뿌리기를 받은 경우
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "already received.")
public class SprinkleAlreadyReceiveException extends RuntimeException {

    private static final long serialVersionUID = -4291959185186540380L;

    public SprinkleAlreadyReceiveException(String message) {
        super(message);
    }
}
