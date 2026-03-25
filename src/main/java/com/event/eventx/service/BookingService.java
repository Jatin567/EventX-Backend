package com.event.eventx.service;

import com.event.eventx.dto.BookingDto;
import java.util.List;

public interface BookingService {
    BookingDto bookTicket(BookingDto bookingDto);
    List<BookingDto> getBookingsByUserId(Long userId);
}
