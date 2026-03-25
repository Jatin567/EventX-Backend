package com.event.eventx.service.impl;

import com.event.eventx.dto.EventDto;
import com.event.eventx.entity.Event;
import com.event.eventx.exception.ResourceNotFoundException;
import com.event.eventx.repository.EventRepository;
import com.event.eventx.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventDto createEvent(EventDto eventDto) {
        Event event = mapToEntity(eventDto);
        Event savedEvent = eventRepository.save(event);
        return mapToDto(savedEvent);
    }

    @Override
    public EventDto getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        return mapToDto(event);
    }

    @Override
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto updateEvent(Long eventId, EventDto updatedEvent) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setLocation(updatedEvent.getLocation());
        event.setState(updatedEvent.getState());
        event.setCountry(updatedEvent.getCountry());
        event.setEventDate(updatedEvent.getEventDate());
        event.setTotalSeats(updatedEvent.getTotalSeats());
        event.setAvailableSeats(updatedEvent.getAvailableSeats());
        event.setPrice(updatedEvent.getPrice());
        event.setImageUrl(updatedEvent.getImageUrl());

        Event savedEvent = eventRepository.save(event);
        return mapToDto(savedEvent);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        eventRepository.delete(event);
    }

    private EventDto mapToDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getState(),
                event.getCountry(),
                event.getEventDate(),
                event.getTotalSeats(),
                event.getAvailableSeats(),
                event.getPrice(),
                event.getImageUrl()
        );
    }

    private Event mapToEntity(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setLocation(eventDto.getLocation());
        event.setState(eventDto.getState());
        event.setCountry(eventDto.getCountry());
        event.setEventDate(eventDto.getEventDate());
        event.setTotalSeats(eventDto.getTotalSeats());
        event.setAvailableSeats(eventDto.getAvailableSeats() != null ? eventDto.getAvailableSeats() : eventDto.getTotalSeats());
        event.setPrice(eventDto.getPrice());
        event.setImageUrl(eventDto.getImageUrl());
        return event;
    }
}
