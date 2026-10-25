package com.example.backend.dto;

import java.math.BigDecimal;

public record CalcResponse(
        BigDecimal total,
        BigDecimal profit
) {
}