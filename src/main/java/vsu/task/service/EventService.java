package vsu.task.service;

import vsu.task.domain.Birthday;
import vsu.task.domain.Event;
import vsu.task.domain.EventType;
import vsu.task.domain.Meeting;
import vsu.task.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void addEvent(EventType type, LocalDate date, String description, String extraData) {
        Event event;
        if (type == EventType.BIRTHDAY) {
            event = new Birthday(date, description, extraData); // extraData-имя, так можно? читаемо?
        } else {
            event = new Meeting(date, description, extraData); // extraData- место
        }
        eventRepository.addEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    public List<Event> getEventsByDate(LocalDate date) {
        return eventRepository.getEventsByDate(date);
    }

    public Event getEventById(long id) {
        return eventRepository.getEventById(id);
    }

    public void updateEvent(Event event) {
        eventRepository.updateEvent(event);
    }

    public void deleteEvent(long id) {
        eventRepository.removeEvent(id);
    }
}
