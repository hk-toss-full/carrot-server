package com.carrot.payment.controller;

import com.carrot.payment.domain.Payment;
import com.carrot.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;

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
