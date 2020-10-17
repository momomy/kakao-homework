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
     * User Xid
     */
    private final long userXid;
    /**
     * Room Xid
     */
    private final String roomXid;
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
        return Joiner.on('-').join(userXid, roomXid);
    }

    public static SprinkleCreateVo of(long userXid, String roomXid, int count, BigDecimal amount) {
        return new SprinkleCreateVo(userXid, roomXid, count, amount);
    }
}
