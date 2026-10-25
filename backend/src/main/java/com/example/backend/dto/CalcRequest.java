package com.example.backend.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalcRequest(
        @NotNull(message = "Укажите сумму вклада")
        @DecimalMin(value = "1000", message = "Минимальная сумма вклада: 1 000 ₽")
        @DecimalMax(value = "10000000", message = "Максимальная сумма вклада: 10 000 000 ₽")
        BigDecimal amount,

        @NotNull(message = "Укажите срок вклада")
        @Min(value = 1, message = "Минимальный срок: 1 месяц")
        @Max(value = 60, message = "Максимальный срок: 60 месяцев")
        Integer months,

        @NotNull(message = "Укажите процентную годовую ставку")
        @DecimalMin(value = "1.0", message = "Минимальная ставка: 1%")
        @DecimalMax(value = "20.0", message = "Максимальная ставка: 20%")
        BigDecimal rate
) {
}