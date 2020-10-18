package me.kakaopay.homework.controller.sprinkle.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SprinkleCreateResponseDto {
    /**
     * Token
     */
    private final String token;

    /**
     * 만료일시
     */
    private final LocalDateTime expireAt;

    public static SprinkleCreateResponseDto of(String token, LocalDateTime expireAt) {
        return new SprinkleCreateResponseDto(token, expireAt);
    }
}
