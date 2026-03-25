package com.event.eventx.service;

import com.event.eventx.dto.EventDto;
import java.util.List;

public interface EventService {
    List<EventDto> getAllEvents();
    EventDto getEventById(Long id);
    EventDto createEvent(EventDto eventDto);
    EventDto updateEvent(Long id, EventDto eventDto);
    void deleteEvent(Long id);
}
