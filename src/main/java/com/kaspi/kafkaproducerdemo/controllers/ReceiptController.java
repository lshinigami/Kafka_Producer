package com.kaspi.kafkaproducerdemo.controllers;

import com.kaspi.kafkaproducerdemo.domain.dto.ReceiptDto;
import com.kaspi.kafkaproducerdemo.services.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;


    @PostMapping
    public ResponseEntity<Void> createReceipt(@RequestBody ReceiptDto receiptDto) {
        receiptService.createReceipt(receiptDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
