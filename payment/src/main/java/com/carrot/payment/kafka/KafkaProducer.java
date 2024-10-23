package com.carrot.payment.kafka;

import com.carrot.payment.domain.Payment;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements KafkaService {

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private static final String topic = "payment_topic";  // Topic 설정

    @Override
    public void approve(Long id) {
        Payment payment = Payment.builder()
                .id(id)
                .build();

        KafkaMessage message = new KafkaMessage(LocalDateTime.now(), payment, "approve");
        kafkaTemplate.send(topic, message);
    }

    @Override
    public void cancel(Long id) {
        Payment payment = Payment.builder()
                .id(id)
                .build();

        KafkaMessage message = new KafkaMessage(LocalDateTime.now(), payment, "cancel");
        kafkaTemplate.send(topic, message);
    }

    @Override
    public void create(Payment payment) {
        KafkaMessage message = new KafkaMessage(LocalDateTime.now(), payment, "create");
        kafkaTemplate.send(topic, message);
    }
}
