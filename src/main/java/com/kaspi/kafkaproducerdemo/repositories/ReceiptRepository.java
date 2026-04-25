package com.kaspi.kafkaproducerdemo.repositories;

import com.kaspi.kafkaproducerdemo.domain.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {

}
