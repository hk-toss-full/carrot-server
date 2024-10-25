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
    public List<Payment> getUserPaymentsHistory(Long userId) {
        return paymentRepository.findAllByUserId(userId);
    }

    @Override
    public List<Payment> getPendingPayments() {
        return paymentRepository.findAllByStatus(PaymentStatus.REQUESTED);
    }
}
