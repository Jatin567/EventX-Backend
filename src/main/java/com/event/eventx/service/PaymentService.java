package com.event.eventx.service;

import com.event.eventx.dto.PaymentDto;

public interface PaymentService {
    PaymentDto processPayment(PaymentDto paymentDto);
    PaymentDto getPaymentByBookingId(Long bookingId);
}
