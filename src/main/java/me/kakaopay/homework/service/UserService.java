package me.kakaopay.homework.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.entity.User;
import me.kakaopay.homework.exception.UserNotFoundException;
import me.kakaopay.homework.repository.user.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public User getUser(long xid) {
        return findUser(xid)
                .orElseThrow(() -> new UserNotFoundException("not found user. xid: " + xid));
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<User> findUser(long xid) {
        return userRepository.findByXid(xid);
    }
}
