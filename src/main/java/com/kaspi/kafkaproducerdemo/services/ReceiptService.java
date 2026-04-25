package com.kaspi.kafkaproducerdemo.services;

import com.kaspi.kafkaproducerdemo.domain.dto.ReceiptDto;
import com.kaspi.kafkaproducerdemo.domain.entities.Receipt;
import com.kaspi.kafkaproducerdemo.repositories.ReceiptRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.kaspi.kafkaproducerdemo.utils.mappers.ReceiptMapper.mapDtoToReceipt;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final KafkaTemplate<String, Receipt> kafkaTemplate;
    private final ApplicationEventPublisher eventPublisher;

    private final ReceiptRepository receiptRepository;

    @Transactional
    public void createReceipt(ReceiptDto receiptDto) {
        Receipt receipt = mapDtoToReceipt(receiptDto);
        receiptRepository.save(receipt);
        eventPublisher.publishEvent(receipt);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendReceiptToTopic(Receipt receipt) {
        kafkaTemplate.send("receipts", String.valueOf(receipt.getReceiptNumber()), receipt);
    }
}
