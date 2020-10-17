package me.kakaopay.homework.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.entity.Room;
import me.kakaopay.homework.exception.RoomNotFoundException;
import me.kakaopay.homework.repository.room.RoomRepository;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Room getRoom(String xid) {
        return findRoom(xid)
                .orElseThrow(() -> new RoomNotFoundException("not found room. xid: " + xid));
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<Room> findRoom(String xid) {
        return roomRepository.findByXid(xid);
    }
}
