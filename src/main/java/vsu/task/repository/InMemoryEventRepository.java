package vsu.task.repository;

import vsu.task.domain.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryEventRepository implements EventRepository {
    
    private final Map<Long, Event> eventsById = new HashMap<>();

    @Override
    public void addEvent(Event event) {
        eventsById.put(event.getId(), event);
    }

    @Override
    public void removeEvent(long id) {
        eventsById.remove(id);
    }

    @Override
    public Event getEventById(long id) {
        return eventsById.get(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return new ArrayList<>(eventsById.values());
    }

    @Override
    public List<Event> getEventsByDate(LocalDate date) {
        return eventsById.values().stream()
                .filter(e -> e.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public void updateEvent(Event event) {
        if (eventsById.containsKey(event.getId())) {
            eventsById.put(event.getId(), event);
        }
    }
}
