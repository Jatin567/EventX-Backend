package com.event.eventx.service.impl;

import com.event.eventx.dto.BookingDto;
import com.event.eventx.entity.Booking;
import com.event.eventx.entity.Event;
import com.event.eventx.entity.Ticket;
import com.event.eventx.exception.ResourceNotFoundException;
import com.event.eventx.repository.BookingRepository;
import com.event.eventx.repository.EventRepository;
import com.event.eventx.repository.TicketRepository;
import com.event.eventx.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired BookingRepository bookingRepository;
    @Autowired EventRepository eventRepository;
    @Autowired TicketRepository ticketRepository;

    @Override
    @Transactional
    public BookingDto bookTicket(BookingDto bookingDto) {
        Event event = eventRepository.findById(bookingDto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        if (event.getAvailableSeats() < bookingDto.getNumberOfTickets()) {
            throw new RuntimeException("Not enough seats available");
        }

        // 1. Create Booking
        Booking booking = new Booking();
        booking.setUserId(bookingDto.getUserId());
        booking.setEventId(bookingDto.getEventId());
        booking.setNumberOfTickets(bookingDto.getNumberOfTickets());
        booking.setBookingDate(LocalDateTime.now());
        booking.setBookingStatus("CONFIRMED");
        
        Booking savedBooking = bookingRepository.save(booking);

        // 2. Decrease available seats
        event.setAvailableSeats(event.getAvailableSeats() - bookingDto.getNumberOfTickets());
        eventRepository.save(event);

        // 3. Generate Ticket
        Ticket ticket = new Ticket();
        ticket.setBookingId(savedBooking.getId());
        ticket.setTicketNumber(UUID.randomUUID().toString());
        ticket.setSeatNumber("S-" + UUID.randomUUID().toString().substring(0, 4));
        ticketRepository.save(ticket);

        return mapToDto(savedBooking);
    }

    @Override
    public List<BookingDto> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private BookingDto mapToDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUserId());
        dto.setEventId(booking.getEventId());
        dto.setNumberOfTickets(booking.getNumberOfTickets());
        dto.setBookingDate(booking.getBookingDate());
        dto.setBookingStatus(booking.getBookingStatus());
        return dto;
    }
}
