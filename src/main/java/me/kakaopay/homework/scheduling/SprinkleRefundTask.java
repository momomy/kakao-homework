package me.kakaopay.homework.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.service.sprinkle.SprinkleRefundService;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleRefundVo;

@RequiredArgsConstructor
@Component
public class SprinkleRefundTask {

    private final SprinkleRefundService sprinkleRefundService;

    @Scheduled(fixedDelay = 1000 * 60)
    public void refundTask() {
        sprinkleRefundService.refund(new SprinkleRefundVo());
    }
}
