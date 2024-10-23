package com.carrot.payment.repository;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.domain.PaymentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByUserId(Long userId);
    List<Payment> findAllByPaymentStatus(PaymentStatus status);
}
