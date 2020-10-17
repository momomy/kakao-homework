package me.kakaopay.homework.service.sprinkle;

import java.text.MessageFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.common.lock.DistributeLock;
import me.kakaopay.homework.common.lock.LockCategory;
import me.kakaopay.homework.exception.SprinkleNotFoundException;
import me.kakaopay.homework.repository.sprinkle.BalanceSprinkleRepository;
import me.kakaopay.homework.service.UserService;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleCreateVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@RequiredArgsConstructor
@Service
public class SprinkleService {

    private final UserService userService;

    private final BalanceSprinkleRepository balanceSprinkleRepository;

    @DistributeLock(LockCategory.SPRINKLE_CREATE)
    @Transactional
    public SprinkleVo create(SprinkleCreateVo vo) {
        return null;
    }

    @DistributeLock(LockCategory.SPRINKLE_RECEIVE)
    @Transactional
    public SprinkleVo receive(SprinkleCreateVo vo) {
        return null;
    }

    @Transactional(readOnly = true)
    public SprinkleVo get(long userXid, String token) {
        return balanceSprinkleRepository
                .find(userService.getUser(userXid), token)
                .map(SprinkleVo::of)
                .orElseThrow(() -> new SprinkleNotFoundException(
                        MessageFormat.format("not found sprinkle. userXid: {0}, token: {1}", userXid, token)));
    }

}
