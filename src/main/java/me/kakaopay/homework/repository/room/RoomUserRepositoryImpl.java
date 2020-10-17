package me.kakaopay.homework.repository.room;

import java.util.Optional;

import me.kakaopay.homework.entity.QRoom;
import me.kakaopay.homework.entity.QRoomUser;
import me.kakaopay.homework.entity.QUser;
import me.kakaopay.homework.entity.RoomUser;
import me.kakaopay.homework.repository.AbstractRepositorySupport;

public class RoomUserRepositoryImpl extends AbstractRepositorySupport implements RoomUserRepositoryCustom {

    private static final QRoomUser ROOM_USER = QRoomUser.roomUser;

    private static final QRoom ROOM = QRoom.room;

    private static final QUser USER = QUser.user;

    public RoomUserRepositoryImpl() {
        super(RoomUser.class);
    }

    @Override
    public Optional<RoomUser> find(String roomXid, long userXid) {
        return Optional.ofNullable(
                from(ROOM_USER)
                        .innerJoin(ROOM_USER.room, ROOM).on(ROOM.xid.eq(roomXid))
                        .innerJoin(ROOM_USER.user, USER).on(USER.xid.eq(userXid))
                        .select(ROOM_USER)
                        .fetchOne());
    }
}
