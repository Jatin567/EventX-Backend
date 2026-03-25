package com.event.eventx.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

public class BookingDto {
    private Long id;
    @NotNull private Long userId;
    @NotNull private Long eventId;
    @NotNull @Min(1) private Integer numberOfTickets;
    private LocalDateTime bookingDate;
    private String bookingStatus;

    public BookingDto() {}

    public BookingDto(Long id, Long userId, Long eventId, Integer numberOfTickets, LocalDateTime bookingDate, String bookingStatus) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.numberOfTickets = numberOfTickets;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public Integer getNumberOfTickets() { return numberOfTickets; }
    public void setNumberOfTickets(Integer numberOfTickets) { this.numberOfTickets = numberOfTickets; }
    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
}
