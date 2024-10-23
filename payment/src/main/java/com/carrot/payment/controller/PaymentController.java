package com.carrot.payment.controller;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.service.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;

    @PostMapping("/start")
    public ResponseEntity<Payment> startPayment(@RequestParam Long chatRoomId,
                                                @RequestParam Long userId,
                                                @RequestParam Long price) {
        Payment payment = service.startPayment(chatRoomId, userId, price);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/{paymentId}/approve")
    public ResponseEntity<Void> approvePayment(@PathVariable Long paymentId) {
        service.approvePayment(paymentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long paymentId) {
        service.cancelPayment(paymentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getUserPaymentHistory(@PathVariable Long userId) {
        List<Payment> payments = service.getUserPaymentsHistory(userId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Payment>> getPendingPayments() {
        List<Payment> payments = service.getPendingPayments();
        return ResponseEntity.ok(payments);
    }
}
