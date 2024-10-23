package com.carrot.payment.kafka;

import com.carrot.payment.domain.Payment;

public interface KafkaService {
    void approve(Long paymentId);   // 승인 메시지 전송
    void cancel(Long paymentId);    // 취소 메시지 전송
    void create(Payment payment);   // 송금 생성 메시지 전송
}
