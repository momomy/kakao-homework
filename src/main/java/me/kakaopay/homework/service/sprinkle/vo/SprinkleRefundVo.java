package me.kakaopay.homework.service.sprinkle.vo;

import me.kakaopay.homework.common.lock.Lockable;

public class SprinkleRefundVo implements Lockable {
    @Override
    public String getLockKey() {
        return "dummy2";
    }
}
