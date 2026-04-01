package vsu.task.repository;

import vsu.task.domain.Event;
import java.time.LocalDate;
import java.util.List;

public interface EventRepository {
    void addEvent(Event event);
    void removeEvent(long id);
    Event getEventById(long id);
    List<Event> getAllEvents();
    List<Event> getEventsByDate(LocalDate date);
    void updateEvent(Event event);
}