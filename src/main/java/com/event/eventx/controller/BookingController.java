package com.event.eventx.controller;

import com.event.eventx.dto.BookingDto;
import com.event.eventx.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> bookTicket(@Valid @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.bookTicket(bookingDto));
    }

    @GetMapping("/user/{userId}")
    public List<BookingDto> getBookingsByUserId(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }
}
