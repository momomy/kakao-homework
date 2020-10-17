package me.kakaopay.homework.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.kakaopay.homework.entity.RoomUser;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long>, RoomUserRepositoryCustom {
}
