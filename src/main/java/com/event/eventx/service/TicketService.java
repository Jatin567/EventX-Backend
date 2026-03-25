package com.event.eventx.service;

import com.event.eventx.entity.Ticket;
import java.util.List;

public interface TicketService {
    List<Ticket> getTicketsByBookingId(Long bookingId);
}
