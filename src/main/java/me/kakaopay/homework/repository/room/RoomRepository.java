package me.kakaopay.homework.repository.room;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.kakaopay.homework.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByXid(String xid);
}
