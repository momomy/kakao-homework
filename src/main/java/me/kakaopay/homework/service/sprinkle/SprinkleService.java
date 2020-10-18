package me.kakaopay.homework.service.sprinkle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.kakaopay.homework.entity.BalanceSprinkle;
import me.kakaopay.homework.entity.BalanceTransaction;
import me.kakaopay.homework.repository.sprinkle.BalanceSprinkleRepository;

@RequiredArgsConstructor
@Service
public class SprinkleService {

    private final BalanceSprinkleRepository sprinkleRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public BalanceSprinkle insert(String token,
                                  long userId,
                                  String roomId,
                                  int count,
                                  BigDecimal amount,
                                  LocalDateTime expiredAt,
                                  BalanceTransaction sprinkleTransaction) {
        return sprinkleRepository.save(BalanceSprinkle.create(token,
                                                              userId,
                                                              roomId,
                                                              count,
                                                              amount,
                                                              expiredAt,
                                                              sprinkleTransaction));
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public BalanceSprinkle get(long id) {
        return sprinkleRepository.get(id);
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public List<BalanceSprinkle> findNotRefund() {
        return sprinkleRepository.findNotRefund();
    }
}
