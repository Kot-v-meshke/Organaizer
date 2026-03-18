package vsu.task.domain;

import java.time.LocalDate;

public class Meeting extends Event {

    private String location;

    public Meeting(LocalDate date, String description) {
        super(date, description);
    }

    @Override
    public EventType getType() {
        return EventType.MEETING;
    }

    public Meeting(LocalDate date, String description, String location) {
        super(date, description);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
