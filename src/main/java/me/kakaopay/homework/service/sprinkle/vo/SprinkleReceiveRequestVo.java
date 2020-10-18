package me.kakaopay.homework.service.sprinkle.vo;

import com.google.common.base.Joiner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.kakaopay.homework.common.lock.Lockable;

@ToString
@AllArgsConstructor
@Getter
public class SprinkleReceiveRequestVo implements Lockable {
    /**
     * User ID
     */
    private final long userId;
    /**
     * Room ID
     */
    private final String roomId;
    /**
     * Token
     */
    private final String token;

    @Override
    public String getLockKey() {
        return Joiner.on('-').join(roomId, token);
    }

    public static SprinkleReceiveRequestVo of(long userId, String roomId, String token) {
        return new SprinkleReceiveRequestVo(userId, roomId, token);
    }
}
