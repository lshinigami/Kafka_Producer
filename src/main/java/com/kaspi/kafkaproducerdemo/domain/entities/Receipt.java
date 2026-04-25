package com.kaspi.kafkaproducerdemo.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kaspi.kafkaproducerdemo.domain.enums.Currency;
import com.kaspi.kafkaproducerdemo.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "receipts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "receipt_number", unique = true)
    private UUID receiptNumber;

    @Column(name = "description")
    private String description;

    public UUID getReceiptNumber() {
        return receiptNumber;
    }

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "issuer")
    private String issuer;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "tax_amount", precision = 19, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "discount_amount", precision = 19, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", length = 3)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "merchant_bin")
    private String merchantBin;

    @Column(name = "terminal_id")
    private String terminalId;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @PrePersist
    protected void onCreate() {
        this.issuedAt = LocalDateTime.now();
    }
}