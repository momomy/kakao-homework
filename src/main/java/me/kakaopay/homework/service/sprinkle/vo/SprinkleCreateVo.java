package me.kakaopay.homework.service.sprinkle.vo;

import java.math.BigDecimal;

import com.google.common.base.Joiner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.kakaopay.homework.common.lock.Lockable;

@AllArgsConstructor
@Getter
public class SprinkleCreateVo implements Lockable {
    /**
     * User ID
     */
    private final long userId;
    /**
     * Room ID
     */
    private final String roomId;
    /**
     * 뿌릴 사람 수
     */
    private final int count;
    /**
     * 뿌릴 금액
     */
    private final BigDecimal amount;

    @Override
    public String getLockKey() {
        return Joiner.on('-').join(userId, roomId);
    }

    public static SprinkleCreateVo of(long userId, String roomId, int count, BigDecimal amount) {
        return new SprinkleCreateVo(userId, roomId, count, amount);
    }
}
