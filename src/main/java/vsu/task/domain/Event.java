package vsu.task.domain;

import java.time.LocalDate;

public abstract class Event {

    private static long idCounter;

    private long id;

    private LocalDate date;

    private String description;

    public Event(LocalDate date, String description) {
        this.date = date;
        this.description = description;
        this.id = idCounter++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract EventType getType();

    @Override
    public String toString() {
        return String.format("%s \nДата: %s \nОписание: %s", getType().getLabel(), date, description);
    }

    public String getDetails() {
        return toString();
    }
}
