package me.kakaopay.homework.controller.sprinkle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class SprinkleDto {
    /**
     * 뿌린시각
     */
    private final LocalDateTime createdAt;

    /**
     * 뿌린금액
     */
    private final BigDecimal amount;

    /**
     * 받기 완료된 정보
     */
    private final List<SprinkleTransactionDto> sprinkledList;

    /**
     * 받기 완료 된 금액
     */
    @SuppressWarnings("unused")
    public BigDecimal getSprinkledAmount() {
        return sprinkledList.stream()
                            .map(SprinkleTransactionDto::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static SprinkleDto of(LocalDateTime createdAt,
                                 BigDecimal amount,
                                 List<SprinkleTransactionDto> sprinkledList) {
        return new SprinkleDto(createdAt, amount, sprinkledList);
    }
}
