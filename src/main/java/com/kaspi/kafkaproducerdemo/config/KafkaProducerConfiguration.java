package com.kaspi.kafkaproducerdemo.config;

import com.kaspi.kafkaproducerdemo.domain.entities.Receipt;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Bean
    public NewTopic receiptTopic() {
        return new NewTopic("receipts",
                1,
                (short) 1
        );
    }


    @Bean
    public ProducerFactory<String, Receipt> producerFactory(ObjectMapper objectMapper) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);

        return getStringReceiptDefaultKafkaProducerFactory(objectMapper, configProps);
    }

    private static @NonNull DefaultKafkaProducerFactory<String, Receipt> getStringReceiptDefaultKafkaProducerFactory(ObjectMapper objectMapper, Map<String, Object> configProps) {
        Serializer<Receipt> valueSerializer = (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize Receipt", e);
            }
        };


        DefaultKafkaProducerFactory<String, Receipt> factory = new DefaultKafkaProducerFactory<>(configProps);
        factory.setValueSerializer(valueSerializer);
        factory.setTransactionIdPrefix("receipt-tx-");
        return factory;
    }


    @Bean
    public KafkaTemplate<String, Receipt> kafkaTemplate(ProducerFactory<String, Receipt> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}