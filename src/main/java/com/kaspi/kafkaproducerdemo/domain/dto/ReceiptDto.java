package com.kaspi.kafkaproducerdemo.domain.dto;

import com.kaspi.kafkaproducerdemo.domain.enums.Currency;
import com.kaspi.kafkaproducerdemo.domain.enums.PaymentMethod;

import java.math.BigDecimal;

public record ReceiptDto(
        String description,
        String fileUrl,
        String issuer,
        Long paymentId,
        String clientEmail,
        BigDecimal amount,
        BigDecimal taxAmount,
        BigDecimal discountAmount,
        BigDecimal totalAmount,
        Currency currency,
        PaymentMethod paymentMethod,
        String merchantId,
        String merchantName,
        String merchantBin,
        String terminalId
) {}