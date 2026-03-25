package com.event.eventx.service.impl;

import com.event.eventx.entity.Ticket;
import com.event.eventx.repository.TicketRepository;
import com.event.eventx.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired TicketRepository ticketRepository;

    @Override
    public List<Ticket> getTicketsByBookingId(Long bookingId) {
        return ticketRepository.findByBookingId(bookingId);
    }
}
