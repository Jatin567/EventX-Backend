package com.event.eventx.controller;

import com.event.eventx.entity.Ticket;
import com.event.eventx.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired TicketService ticketService;

    @GetMapping("/{bookingId}")
    public List<Ticket> getTicketsByBookingId(@PathVariable Long bookingId) {
        return ticketService.getTicketsByBookingId(bookingId);
    }
}
