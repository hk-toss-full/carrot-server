package com.carrot.payment.kafka;

import com.carrot.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PaymentService service;

    @KafkaListener(topics = "payment_topic", groupId = "payment-consumer-group")
    public void processPaymentMessage(KafkaMessage message) {
        // 메시지 출력 (필요시 로그 추가 가능)
        System.out.println("Received Kafka message: " + message);

        // 메시지의 action에 따라 적절한 PaymentService 메서드 호출
        switch (message.getAction()) {

            default:
                throw new IllegalArgumentException("Unknown action: " + message.getAction());
        }
    }
}
