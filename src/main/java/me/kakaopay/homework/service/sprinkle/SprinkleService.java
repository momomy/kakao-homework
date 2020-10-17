package me.kakaopay.homework.service.sprinkle;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.common.lock.DistributeLock;
import me.kakaopay.homework.common.lock.LockCategory;
import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.entity.Room;
import me.kakaopay.homework.entity.User;
import me.kakaopay.homework.exception.SprinkleNotFoundException;
import me.kakaopay.homework.repository.sprinkle.BalanceSprinkleRepository;
import me.kakaopay.homework.service.RoomService;
import me.kakaopay.homework.service.UserService;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleCreateVo;
import me.kakaopay.homework.service.sprinkle.vo.SprinkleVo;

@RequiredArgsConstructor
@Service
public class SprinkleService {

    private static final int TOKEN_LENGTH = 3;

    private static final Duration SPRINKLE_DURATION = Duration.ofMinutes(10);

    private final UserService userService;

    private final RoomService roomService;

    private final SprinkleTokenGenerator tokenGenerator;

    private final BalanceSprinkleRepository sprinkleRepository;

    private final SprinkleAmountManager sprinkleAmountManager;

    @DistributeLock(LockCategory.SPRINKLE_CREATE)
    @Transactional
    public SprinkleVo create(SprinkleCreateVo vo) {
        final User user = userService.getUser(vo.getUserXid());
        final Room room = roomService.getRoom(vo.getRoomXid());
        final String token = tokenGenerator.generate(user, TOKEN_LENGTH);
        final LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(SPRINKLE_DURATION.getSeconds());

        sprinkleAmountManager.distribute(user.getXid(), token, vo.getAmount(), vo.getCount(), expiredAt);

        return SprinkleVo.of(
                sprinkleRepository.save(
                        BalanceSprinkle.create(token,
                                               user,
                                               room,
                                               vo.getCount(),
                                               vo.getAmount(),
                                               expiredAt)));
    }

    @DistributeLock(LockCategory.SPRINKLE_RECEIVE)
    @Transactional
    public SprinkleVo receive(SprinkleCreateVo vo) {
        return null;
    }

    @Transactional(readOnly = true)
    public SprinkleVo get(long userXid, String token) {
        return sprinkleRepository
                .find(userService.getUser(userXid), token)
                .map(SprinkleVo::of)
                .orElseThrow(() -> new SprinkleNotFoundException(
                        MessageFormat.format("not found sprinkle. userXid: {0}, token: {1}", userXid, token)));
    }

}
