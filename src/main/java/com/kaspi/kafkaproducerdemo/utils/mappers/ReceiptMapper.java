package com.kaspi.kafkaproducerdemo.utils.mappers;

import com.kaspi.kafkaproducerdemo.domain.dto.ReceiptDto;
import com.kaspi.kafkaproducerdemo.domain.entities.Receipt;

public class ReceiptMapper {
    public static Receipt mapDtoToReceipt(ReceiptDto receiptDto) {
        return Receipt.builder()
                .description(receiptDto.description())
                .fileUrl(receiptDto.fileUrl())
                .issuer(receiptDto.issuer())
                .paymentId(receiptDto.paymentId())
                .clientEmail(receiptDto.clientEmail())
                .amount(receiptDto.amount())
                .taxAmount(receiptDto.taxAmount())
                .discountAmount(receiptDto.discountAmount())
                .totalAmount(receiptDto.totalAmount())
                .currency(receiptDto.currency())
                .paymentMethod(receiptDto.paymentMethod())
                .merchantId(receiptDto.merchantId())
                .merchantName(receiptDto.merchantName())
                .merchantBin(receiptDto.merchantBin())
                .terminalId(receiptDto.terminalId())
                .build();
    }
}
