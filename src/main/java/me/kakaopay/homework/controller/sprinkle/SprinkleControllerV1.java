package me.kakaopay.homework.controller.sprinkle;

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

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kakaopay.homework.controller.common.AbstractController;
import me.kakaopay.homework.controller.sprinkle.dto.SprinkleCreateRequestDto;
import me.kakaopay.homework.controller.sprinkle.dto.SprinkleCreateResponseDto;
import me.kakaopay.homework.controller.sprinkle.dto.SprinkleDto;
import me.kakaopay.homework.controller.sprinkle.dto.SprinkleTransactionDto;
import me.kakaopay.homework.service.sprinkle.SprinkleService;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleCreateVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleReceiveRequestVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@Api(
        value = "Sprinkle V1 API",
        tags = "Sprinkle V1 API"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sprinkle")
public class SprinkleControllerV1 extends AbstractController {

    private final SprinkleService sprinkleService;

    @PutMapping("/")
    @ResponseBody
    public SprinkleCreateResponseDto create(
            @RequestHeader("X-USER-ID") @NotNull Long userId,
            @RequestHeader("X-ROOM-ID") @NotBlank String roomId,
            @Valid @RequestBody SprinkleCreateRequestDto requestDto) {
        SprinkleVo sprinkleVo = sprinkleService.create(SprinkleCreateVo.of(userId,
                                                                           roomId,
                                                                           requestDto.getCount(),
                                                                           requestDto.getAmount()));
        return SprinkleCreateResponseDto.of(sprinkleVo.getToken(), sprinkleVo.getExpiredAt());
    }

    /**
     * 받기 API
     */
    @PutMapping("/{token}")
    @ResponseBody
    public SprinkleTransactionDto receive(
            @RequestHeader("X-USER-ID") @NotNull Long userId,
            @RequestHeader("X-ROOM-ID") @NotBlank String roomId,
            @PathVariable(value = "token") @NotBlank String token) {
        return sprinkleService.receive(SprinkleReceiveRequestVo.of(userId, roomId, token)).toDto();
    }

    /**
     * 조회 API
     */
    @GetMapping("/{token}")
    @ResponseBody
    public SprinkleDto get(
            @RequestHeader("X-USER-ID") @NotNull Long userId,
            @PathVariable(value = "token") @NotBlank String token) {
        return sprinkleService.get(userId, token).toDto();
    }
}
