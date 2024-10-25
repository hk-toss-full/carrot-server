package com.carrot.payment.service;

import com.carrot.payment.domain.Payment;
import java.util.List;

public interface PaymentService {
    List<Payment> getUserPaymentsHistory(Long userId);

    List<Payment> getPendingPayments();
}
