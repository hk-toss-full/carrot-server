package com.carrot.payment.service;

import com.carrot.payment.domain.Payment;
import java.util.List;

public interface PaymentService {
    Payment startPayment(Long chatRoomId, Long userId, Long price);

    void approvePayment(Long paymentId);

    void cancelPayment(Long paymentId);

    List<Payment> getUserPaymentsHistory(Long userId);

    List<Payment> getPendingPayments();
}
