package com.carrot.payment.service;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.domain.PaymentStatus;
import com.carrot.payment.kafka.KafkaService;
import com.carrot.payment.repository.PaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaService kafkaService;

    @Override
    public Payment startPayment(Long chatRoomId, Long userId, Long price) {
        Payment payment = Payment.builder()
                .userId(userId)
                .chatRoomId(chatRoomId)
                .price(price)
                .status(PaymentStatus.REQUESTED)
                .createdAt(LocalDateTime.now())
                .build();
        // Payment 저장
        Payment savedPayment = paymentRepository.save(payment);

        // Kafka로 송금 생성 메시지 전송
        kafkaService.create(savedPayment);

        return savedPayment;
    }

    @Override
    public void approvePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        // 객체를 새로 생성하면서 필요한 필드 값만 변경
        Payment updatedPayment = Payment.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .chatRoomId(payment.getChatRoomId())
                .price(payment.getPrice())
                .status(PaymentStatus.APPROVED)
                .createdAt(payment.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        paymentRepository.save(updatedPayment);

        // Kafka로 승인 메시지 전송
        kafkaService.approve(id);
    }

    @Override
    public void cancelPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        // 객체를 새로 생성하면서 필요한 필드 값만 변경
        Payment updatedPayment = Payment.builder()
                .id(payment.getId())
                .userId(payment.getUserId())
                .chatRoomId(payment.getChatRoomId())
                .price(payment.getPrice())
                .status(PaymentStatus.CANCELED)
                .createdAt(payment.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        paymentRepository.save(updatedPayment);

        // Kafka로 취소 메시지 전송
        kafkaService.cancel(id);
    }

    @Override
    public List<Payment> getUserPaymentsHistory(Long userId) {
        return paymentRepository.findAllByUserId(userId);
    }

    @Override
    public List<Payment> getPendingPayments() {
        return paymentRepository.findAllByPaymentStatus(PaymentStatus.REQUESTED);
    }
}
