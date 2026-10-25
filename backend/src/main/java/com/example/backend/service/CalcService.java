package com.example.backend.service;

import com.example.backend.dto.CalcRequest;
import com.example.backend.dto.CalcResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class CalcService {
    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final MathContext MC = new MathContext(20, RoundingMode.HALF_UP);

    public CalcResponse calculate(CalcRequest request) {
        BigDecimal amount = request.amount();
        int months = request.months();
        BigDecimal rate = request.rate();

        BigDecimal monthRate = rate
                .divide(HUNDRED, MC)
                .divide(MONTHS_IN_YEAR, MC);

        BigDecimal base = BigDecimal.ONE.add(monthRate, MC);

        BigDecimal factor = base.pow(months, MC);

        BigDecimal total = amount.multiply(factor, MC)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal profit = total.subtract(amount)
                .setScale(2, RoundingMode.HALF_UP);

        return new CalcResponse(total, profit);
    }
}