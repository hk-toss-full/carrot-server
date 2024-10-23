package com.carrot.payment.kafka;

import com.carrot.payment.domain.Payment;
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
            case "create":
                // create action에 대한 처리
                Payment payment = message.getPayment();
                service.startPayment(payment.getChatRoomId(), payment.getUserId(), payment.getPrice());
                break;

            case "approve":
                // approve action에 대한 처리
                service.approvePayment(message.getPayment().getId());
                break;

            case "cancel":
                // cancel action에 대한 처리
                service.cancelPayment(message.getPayment().getId());
                break;

            default:
                throw new IllegalArgumentException("Unknown action: " + message.getAction());
        }
    }
}
