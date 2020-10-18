package me.kakaopay.homework.service.sprinkle;

import java.text.MessageFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.service.sprinkle.exception.SprinkleNotFoundException;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@RequiredArgsConstructor
@Service
public class SprinkleLookupService {

    private final SprinkleService sprinkleService;

    private final SprinkleCacheManager sprinkleCacheManager;

    @Transactional(readOnly = true)
    public SprinkleVo lookup(long userId, String token) {
        final SprinkleVo sprinkleVo =
                sprinkleCacheManager.get(token)
                                    .filter(vo -> vo.getUserId() == userId)
                                    .orElseThrow(() -> new SprinkleNotFoundException(MessageFormat.format(
                                            "sprinkle not found. userId: {0}, token: {1}", userId, token)));

        return SprinkleVo.of(sprinkleService.get(sprinkleVo.getId()));
    }
}
