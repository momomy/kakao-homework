package me.kakaopay.homework.controller.common;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid parameter")
    protected void handleJsonParseException(HttpMessageNotReadableException e) {
        log.info("invalid parameter.", e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected void handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(e.getMessage(), e);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected void handleBindException(BindException e) {
        log.info(e.getMessage(), e);
    }

    @Order
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "internal server error.")
    protected void handleJsonParseException(Exception e) {
        log.error("internal server error.", e);
    }
}
