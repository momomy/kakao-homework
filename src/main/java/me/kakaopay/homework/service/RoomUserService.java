package me.kakaopay.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.entity.Room;
import me.kakaopay.homework.exception.RoomNotFoundException;
import me.kakaopay.homework.repository.room.RoomUserRepository;

@RequiredArgsConstructor
@Service
public class RoomUserService {

    private final RoomUserRepository roomUserRepository;

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Room getRoomUser(String roomXid, String userXid) {
        return null;
    }
}
