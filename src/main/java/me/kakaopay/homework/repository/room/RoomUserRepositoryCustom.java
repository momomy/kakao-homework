package me.kakaopay.homework.repository.room;

import java.util.Optional;

import me.kakaopay.homework.entity.RoomUser;

public interface RoomUserRepositoryCustom {
    Optional<RoomUser> find(String roomXid, long userXid);
}
