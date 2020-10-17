package me.kakaopay.homework.controller.sprinkle;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.service.sprinkle.SprinkleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/sprinkle")
public class SprinkleControllerV1 {

    private final SprinkleService sprinkleService;

    @PutMapping("/")
    @ResponseBody
    public SprinkleCreateResponseDto create(
            @RequestHeader("X-USER-ID") @NotNull Long userXid,
            @RequestHeader("X-ROOM-ID") @NotBlank String roomXid,
            @Valid @RequestBody SprinkleCreateRequestDto requestDto) {
        return SprinkleCreateResponseDto.of("token", LocalDateTime.now());
    }

    /**
     * 조회 API
     */
    @GetMapping("/{token}")
    @ResponseBody
    public SprinkleDto get(
            @RequestHeader("X-USER-ID") @NotNull Long userXid,
            @PathVariable(value = "token") @NotBlank String token) {
        return sprinkleService.get(userXid, token).toDto();
    }
}