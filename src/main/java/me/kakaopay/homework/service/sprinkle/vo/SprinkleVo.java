package me.kakaopay.homework.service.sprinkle.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import me.kakaopay.homework.controller.sprinkle.SprinkleDto;
import me.kakaopay.homework.entity.BalanceSprinkle;

@ToString
@AllArgsConstructor
@Getter
public class SprinkleVo {
    /**
     * 뿌리기 Token
     */
    private final String token;
    /**
     * 뿌리기를 한 Room Id
     */
    private final String roomXid;
    /**
     * 뿌리기를 한 User Id
     */
    private final long userXid;
    /**
     * 뿌린 금액
     */
    private final BigDecimal amount;
    /**
     * 뿌린 사람 수
     */
    private final int count;
    /**
     * 뿌리기 일시
     */
    private final LocalDateTime createdAt;
    /**
     * 뿌리기 만료일시
     */
    private final LocalDateTime expiredAt;
    /**
     * 뿌린 금액을 받은 Transaction
     */
    private final List<SprinkleTransactionVo> transactionVos;

    /**
     * 만료되었는가?
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }

    public static SprinkleVo of(BalanceSprinkle sprinkle) {
        return new SprinkleVo(sprinkle.getToken(),
                              sprinkle.getRoom().getXid(),
                              sprinkle.getUser().getXid(),
                              sprinkle.getAmount(),
                              sprinkle.getCount(),
                              sprinkle.getCreatedAt(),
                              sprinkle.getExpiredAt(),
                              sprinkle.getTransactions().stream()
                                      .map(SprinkleTransactionVo::of)
                                      .collect(Collectors.toList()));
    }

    public SprinkleDto toDto() {
        return SprinkleDto.of(getCreatedAt(),
                              getAmount(),
                              transactionVos.stream()
                                            .map(SprinkleTransactionVo::toDto)
                                            .collect(Collectors.toList()));
    }
}
