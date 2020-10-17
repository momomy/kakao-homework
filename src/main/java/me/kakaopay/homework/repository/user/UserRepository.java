package me.kakaopay.homework.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.kakaopay.homework.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByXid(Long xid);
}
