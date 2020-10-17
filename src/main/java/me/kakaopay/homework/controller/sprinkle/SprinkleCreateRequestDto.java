package me.kakaopay.homework.controller.sprinkle;

import java.math.BigDecimal;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SprinkleCreateRequestDto {

    @Min(value = 1)
    private int count;

    @Min(value = 1)
    private BigDecimal amount;
}
